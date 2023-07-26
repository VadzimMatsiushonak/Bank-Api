package by.vadzimmatsiushonak.bank.api.facade.impl;

import by.vadzimmatsiushonak.bank.api.exception.*;
import by.vadzimmatsiushonak.bank.api.facade.AuthorizationFacade;
import by.vadzimmatsiushonak.bank.api.model.Verification;
import by.vadzimmatsiushonak.bank.api.model.entity.BankPayment;
import by.vadzimmatsiushonak.bank.api.service.VerificationService;
import by.vadzimmatsiushonak.bank.api.model.entity.User;
import by.vadzimmatsiushonak.bank.api.model.entity.auth.Role;
import by.vadzimmatsiushonak.bank.api.model.entity.base.UserStatus;
import by.vadzimmatsiushonak.bank.api.service.Oauth2TokenStore;
import by.vadzimmatsiushonak.bank.api.service.UserService;
import by.vadzimmatsiushonak.bank.api.util.JwtTokenUtil;
import com.sun.security.auth.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import java.util.Map;

import static by.vadzimmatsiushonak.bank.api.util.ExceptionUtils.new_BadRequestException;
import static by.vadzimmatsiushonak.bank.api.util.ExceptionUtils.new_EntityNotFoundException;
import static by.vadzimmatsiushonak.bank.api.util.ExceptionUtils.new_InvalidCredentialsException;
import static by.vadzimmatsiushonak.bank.api.util.ExceptionUtils.new_UserNotFoundException;
import static by.vadzimmatsiushonak.bank.api.util.NumberUtils.VERIFICATION_MAX_VALUE;
import static by.vadzimmatsiushonak.bank.api.util.NumberUtils.VERIFICATION_MIN_VALUE;

@AllArgsConstructor
@Validated
@Slf4j
@Service
public class AuthorizationFacadeImpl implements AuthorizationFacade {

    public final static String LOGIN_KEY = "L_";
    public final static String REGISTRATION_KEY = "R_";
    public final static String METADATA_KEY = "userId";

    private final UserService userServices;
    private final UserDetailsService userDetailsService;
    private final VerificationService verificationService;
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

        return verificationService.generateCode(Map.of(METADATA_KEY, user), LOGIN_KEY);
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
        Verification verification = verificationService.verifyCode(key, code);

        UserDetails user = userDetailsService.loadUserByUsername(
                ((User)verification.getMetaData().get(METADATA_KEY)).getLogin());

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
     * Provides key and sends code after saving inactive user entity
     * with provided parameters to the database
     *
     * @param user the user entity
     * @return the String response containing the UUID key for the verification request
     */
    @Override
    @Transactional
    public String register(@NotNull User user) {
        user.setRole(Role.TECHNICAL_USER);
        userServices.save(user);

        return verificationService.generateCode(Map.of(METADATA_KEY, user), REGISTRATION_KEY);
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
        Verification verification = verificationService.verifyCode(key, code);
        Long verifyUserId = ((BankPayment)verification.getMetaData().get(METADATA_KEY)).getId();

        User user = userServices.findById(verifyUserId)
                .orElseThrow(() -> new_EntityNotFoundException("User", verifyUserId));

        user.setStatus(UserStatus.ACTIVE);
        log.info("User with key {} has been successfully verified", key);

        return true;
    }

}
