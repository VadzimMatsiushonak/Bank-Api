package by.vadzimmatsiushonak.bank.api.facade.impl;

import by.vadzimmatsiushonak.bank.api.facade.AuthorizationFacade;
import by.vadzimmatsiushonak.bank.api.model.UserConfirmation;
import by.vadzimmatsiushonak.bank.api.model.entity.Customer;
import by.vadzimmatsiushonak.bank.api.model.entity.User;
import by.vadzimmatsiushonak.bank.api.model.entity.auth.Role;
import by.vadzimmatsiushonak.bank.api.model.entity.base.UserStatus;
import by.vadzimmatsiushonak.bank.api.service.CustomerService;
import by.vadzimmatsiushonak.bank.api.service.UserService;
import by.vadzimmatsiushonak.bank.api.util.JwtTokenUtil;
import com.sun.security.auth.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    private final static String LOGIN_KEY = "L_";
    private final static String REGISTRATION_KEY = "R_";

    private final CustomerService customerService;
    private final UserService userServices;
    private final UserDetailsService userDetailsService;
    private final Cache confirmations;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public String authenticate(String username, String password) {
        User user = userServices.findByUsername(username)
                .orElseThrow(() -> new_UserNotFoundException(username));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new_InvalidCredentialsException();
        }

        return generateCode(user, LOGIN_KEY);
    }

    @Override
    public String getToken(String key, Integer code) {

        UserConfirmation confirmation = confirmCode(key, code);

        UserDetails user;
        try {
            user = userDetailsService.loadUserByUsername(confirmation.getUsername());
        } catch (UsernameNotFoundException exception) {
            throw new_UserNotFoundException(confirmation.getUsername());
        }

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                new UserPrincipal(user.getUsername()), null, user.getAuthorities());
        Jwt jwt = jwtTokenUtil.generateJwtToken(auth);

        return jwt.getTokenValue();
    }

    @Override
    @Transactional
    public String register(@NotNull Customer customer) {
        customerService.create(customer);

        User user = new User(customer);
        user.setRole(Role.TECHNICAL_USER);
        userServices.create(user);

        return generateCode(user, REGISTRATION_KEY);
    }

    @Override
    @Transactional
    public Boolean confirmRegistration(@NotBlank String key,
                                       @Min(VERIFICATION_MIN_VALUE) @Max(VERIFICATION_MAX_VALUE) Integer code) {
        UserConfirmation confirmation = confirmCode(key, code);

        User user = userServices.findById(confirmation.getId())
                .orElseThrow(() -> new_EntityNotFoundException("User", confirmation.getId()));
        user.setStatus(UserStatus.ACTIVE);
        log.info("User with key {} has been successfully confirmed", key);

        return true;
    }

    @Override
    public String generateCode(User user, String prefix) {
        String key = UUID.randomUUID().toString();
        if (prefix != null) {
            key = prefix.concat(key);
        }
        Integer code = getRandom(VERIFICATION_MIN_VALUE, VERIFICATION_MAX_VALUE);
        confirmations.put(key, new UserConfirmation(user.getId(), user.getLogin(), code));
        log.info(
                "Confirmation code '{}' has been prepared for user with key '{}' and id '{}'",
                code, key, user.getId());

        return key;
    }

    @Override
    public UserConfirmation confirmCode(String key, Integer code) {
        UserConfirmation confirmation = confirmations.get(key, UserConfirmation.class);

        if (confirmation == null) {
            log.info("Confirmation {} not found or expired", key);
            throw new_ConfirmationNotFoundException(key);
        }

        if (!confirmation.getCode().equals(code)) {
            log.info("Invalid verification code {} for key {}", code, key);
            throw new_InvalidConfirmationException(String.valueOf(code), key);
        }

        confirmations.evictIfPresent(key);

        return confirmation;
    }
}
