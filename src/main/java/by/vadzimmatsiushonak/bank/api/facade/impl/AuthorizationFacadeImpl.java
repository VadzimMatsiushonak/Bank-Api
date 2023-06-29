package by.vadzimmatsiushonak.bank.api.facade.impl;

import by.vadzimmatsiushonak.bank.api.exception.*;
import by.vadzimmatsiushonak.bank.api.facade.AuthorizationFacade;
import by.vadzimmatsiushonak.bank.api.model.UserVerification;
import by.vadzimmatsiushonak.bank.api.model.entity.Customer;
import by.vadzimmatsiushonak.bank.api.model.entity.User;
import by.vadzimmatsiushonak.bank.api.model.entity.auth.Role;
import by.vadzimmatsiushonak.bank.api.model.entity.base.UserStatus;
import by.vadzimmatsiushonak.bank.api.service.CustomerService;
import by.vadzimmatsiushonak.bank.api.service.Oauth2TokenStore;
import by.vadzimmatsiushonak.bank.api.service.UserService;
import by.vadzimmatsiushonak.bank.api.util.JwtTokenUtil;
import com.sun.security.auth.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

import static by.vadzimmatsiushonak.bank.api.util.ExceptionUtils.*;
import static by.vadzimmatsiushonak.bank.api.util.NumberUtils.*;

@AllArgsConstructor
@Validated
@Slf4j
@Service
public class AuthorizationFacadeImpl implements AuthorizationFacade {

    public final static String LOGIN_KEY = "L_";
    public final static String REGISTRATION_KEY = "R_";

    private final CustomerService customerService;
    private final UserService userServices;
    private final UserDetailsService userDetailsService;
    private final Cache verificationCache;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final Oauth2TokenStore tokenService;

    /**
     * Authenticates a user by their username and password.
     * <p>
     * Provides key and sends code after verifying
     * provided parameters are equals to the database entity
     *
     * @param username The username of the user to authenticate.
     * @param password The password of the user to authenticate.
     * @return the String response containing the UUID key for the token retrieval request
     * @throws UserNotFoundException       If the user cannot be found using the provided username.
     * @throws InvalidCredentialsException If the provided credentials are invalid.
     */
    @Override
    public String authenticate(@NotBlank String username, @NotBlank String password) {
        User user = userServices.findByUsername(username)
                .orElseThrow(() -> new_UserNotFoundException(username));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new_InvalidCredentialsException();
        }

        return this.generateCode(user, LOGIN_KEY);
    }

    /**
     * Provides token after verifying
     * provided parameters are equals to the cache value
     *
     * @param key  the verification key
     * @param code the verification code
     * @return the String response containing the JWT accessToken
     */
    @Override
    public String getToken(@NotBlank String key,
                           @Min(VERIFICATION_MIN_VALUE) @Max(VERIFICATION_MAX_VALUE) Integer code) {
        UserVerification verification = verifyCode(key, code);

        UserDetails user = userDetailsService.loadUserByUsername(verification.getUsername());

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                new UserPrincipal(user.getUsername()), null, user.getAuthorities());
        Jwt jwt = jwtTokenUtil.generateJwtToken(auth);

        tokenService.save(jwt);

        return jwt.getTokenValue();
    }

    /**
     * Revokes a JWT token by removing it from the token store.
     *
     * @param token the token to revoke
     * @return A boolean indicating whether the token was successfully removed ('true') or token not found ('false').
     * @throws BadRequestException If the provided token is invalid.
     */
    @Override
    public boolean revokeToken(String token) {
        try {
            Jwt jwt = jwtTokenUtil.getJwt(token);
            return tokenService.removeById(jwt.getId());
        } catch (Exception invalid) {
            throw new_BadRequestException("Invalid token provided");
        }
    }

    /**
     * Provides key and sends code after saving customer and inactive user entities
     * with provided parameters to the database
     *
     * @param customer the customer entity
     * @return the String response containing the UUID key for the verification request
     */
    @Override
    @Transactional
    public String register(@NotNull Customer customer) {
        customerService.save(customer);

        User user = new User(customer);
        user.setRole(Role.TECHNICAL_USER);
        userServices.save(user);

        return generateCode(user, REGISTRATION_KEY);
    }

    /**
     * Verifies provided key and code and set the user status to the ACTIVE
     *
     * @param key  the verification key
     * @param code the verification code
     * @return the Boolean response indicating whether the verification was successful.
     * @throws EntityNotFoundException If the user cannot be found using the ID associated with the verification key.
     */
    @Override
    @Transactional
    public Boolean verifyRegistration(@NotBlank String key,
                                      @Min(VERIFICATION_MIN_VALUE) @Max(VERIFICATION_MAX_VALUE) Integer code) {
        UserVerification verification = verifyCode(key, code);

        User user = userServices.findById(verification.getId())
                .orElseThrow(() -> new_EntityNotFoundException("User", verification.getId()));
        user.setStatus(UserStatus.ACTIVE);
        log.info("User with key {} has been successfully verified", key);

        return true;
    }

    /**
     * Provides key and saves code
     * Sends code to the user device/mail
     *
     * @param user   user entity data that will be stored in the cache
     * @param prefix the prefix used for the generated key value
     * @return the String response containing the UUID key stored in cache
     */
    @Override
    public String generateCode(@NotNull User user, String prefix) {
        String key = UUID.randomUUID().toString();
        if (prefix != null) {
            key = prefix.concat(key);
        }
        Integer code = getRandom(VERIFICATION_MIN_VALUE, VERIFICATION_MAX_VALUE);
        verificationCache.put(key, new UserVerification(user.getId(), user.getLogin(), code));
        log.info(
                "Verification code '{}' has been prepared for user with key '{}' and id '{}'",
                code, key, user.getId());

        return key;
    }

    /**
     * Verifies provided key and code with the data in the cache
     *
     * @param key  the verification key
     * @param code the verification code
     * @return the UserVerification response containing information about the verified user
     * @throws VerificationNotFoundException If the verification key cannot be found or has expired.
     * @throws InvalidVerificationException  If the provided verification code is invalid.
     */
    @Override
    public UserVerification verifyCode(@NotBlank String key,
                                       @Min(VERIFICATION_MIN_VALUE) @Max(VERIFICATION_MAX_VALUE) Integer code) {
        UserVerification verification = this.verificationCache.get(key, UserVerification.class);

        if (verification == null) {
            log.info("Verification {} not found or expired", key);
            throw new_VerificationNotFoundException(key);
        }

        if (!verification.getCode().equals(code)) {
            log.info("Invalid verification code {} for key {}", code, key);
            throw new_InvalidVerificationException(String.valueOf(code), key);
        }

        this.verificationCache.evictIfPresent(key);

        return verification;
    }
}
