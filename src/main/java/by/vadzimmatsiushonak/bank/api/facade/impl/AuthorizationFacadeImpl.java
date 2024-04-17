package by.vadzimmatsiushonak.bank.api.facade.impl;

import by.vadzimmatsiushonak.bank.api.exception.BadRequestException;
import by.vadzimmatsiushonak.bank.api.exception.EntityNotFoundException;
import by.vadzimmatsiushonak.bank.api.exception.InvalidCredentialsException;
import by.vadzimmatsiushonak.bank.api.exception.UserNotFoundException;
import by.vadzimmatsiushonak.bank.api.facade.AuthorizationFacade;
import by.vadzimmatsiushonak.bank.api.model.Confirmation;
import by.vadzimmatsiushonak.bank.api.model.entity.base.ModelStatus;
import by.vadzimmatsiushonak.bank.api.service.ConfirmationService;
import by.vadzimmatsiushonak.bank.api.model.entity.User;
import by.vadzimmatsiushonak.bank.api.model.entity.auth.Role;
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

import org.springframework.transaction.annotation.Transactional;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.Map;

import static by.vadzimmatsiushonak.bank.api.constant.MetadataConstants.ID;
import static by.vadzimmatsiushonak.bank.api.constant.MetadataConstants.LOGIN;
import static by.vadzimmatsiushonak.bank.api.util.ExceptionUtils.new_BadRequestException;
import static by.vadzimmatsiushonak.bank.api.util.ExceptionUtils.new_EntityNotFoundException;
import static by.vadzimmatsiushonak.bank.api.util.ExceptionUtils.new_InvalidCredentialsException;
import static by.vadzimmatsiushonak.bank.api.util.ExceptionUtils.new_UserNotFoundException;
import static by.vadzimmatsiushonak.bank.api.util.NumberUtils.CONFIRMATION_MAX_VALUE;
import static by.vadzimmatsiushonak.bank.api.util.NumberUtils.CONFIRMATION_MIN_VALUE;

@AllArgsConstructor
@Validated
@Slf4j
@Service
public class AuthorizationFacadeImpl implements AuthorizationFacade {

    public final static String LOGIN_KEY = "L_";
    public final static String REGISTRATION_KEY = "R_";

    private final UserService userServices;
    private final UserDetailsService userDetailsService;
    private final ConfirmationService confirmationService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final Oauth2TokenStore tokenService;

    /**
     * Authenticates a user by their login and password.
     * <p>
     * Provides key and sends code after confirmation
     * provided parameters are equals to the database entity
     *
     * @param login The login of the user to authenticate.
     * @param password The password of the user to authenticate.
     * @return the String response containing the UUID key for the token retrieval request
     * @throws UserNotFoundException       If the user cannot be found using the provided login.
     * @throws InvalidCredentialsException If the provided credentials are invalid.
     */
    @Override
    public String authenticate(@NotBlank String login, @NotBlank String password) {
        User user = userServices.findByLogin(login)
                .orElseThrow(() -> new_UserNotFoundException(login));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new_InvalidCredentialsException();
        }

        return confirmationService.generateCode(Map.of(LOGIN, user.getLogin()), LOGIN_KEY);
    }

    /**
     * Provides token after confirm
     * provided parameters are equals to the cache value
     *
     * @param key  the confirmation key
     * @param code the confirmation code
     * @return the String response containing the JWT accessToken
     */
    @Override
    public String getToken(@NotBlank String key,
                           @Min(CONFIRMATION_MIN_VALUE) @Max(CONFIRMATION_MAX_VALUE) Integer code) {
        Confirmation confirmation = confirmationService.confirmCode(key, code);

        UserDetails user = userDetailsService.loadUserByUsername(
                (String) confirmation.getMetaData().get(LOGIN));

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
     * @return the String response containing the UUID key for the confirmation request
     */
    @Override
    @Transactional
    public String register(@NotNull User user) {
        user.setRole(Role.TECHNICAL_USER);
        userServices.save(user);

        return confirmationService.generateCode(Map.of(ID, user.getId()), REGISTRATION_KEY);
    }

    /**
     * Confirms provided key and code and set the user status to the ACTIVE
     *
     * @param key  the confirmation key
     * @param code the confirmation code
     * @return the Boolean response indicating whether the confirmation was successful.
     * @throws EntityNotFoundException If the user cannot be found using the ID associated with the confirmation key.
     */
    @Override
    @Transactional
    public Boolean confirmRegistration(@NotBlank String key,
                                       @Min(CONFIRMATION_MIN_VALUE) @Max(CONFIRMATION_MAX_VALUE) Integer code) {
        Confirmation confirmation = confirmationService.confirmCode(key, code);
        Long confirmUserId = (Long) confirmation.getMetaData().get(ID);

        User user = userServices.findById(confirmUserId)
                .orElseThrow(() -> new_EntityNotFoundException("User", confirmUserId));

        user.setStatus(ModelStatus.ACTIVE);
        log.info("User with key {} has been successfully confirmed", key);

        return true;
    }

}
