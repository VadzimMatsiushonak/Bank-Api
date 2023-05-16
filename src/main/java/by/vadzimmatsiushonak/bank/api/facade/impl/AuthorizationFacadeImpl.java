package by.vadzimmatsiushonak.bank.api.facade.impl;

import static by.vadzimmatsiushonak.bank.api.util.ExceptionUtils.new_ConfirmationNotFoundException;
import static by.vadzimmatsiushonak.bank.api.util.ExceptionUtils.new_EntityNotFoundException;
import static by.vadzimmatsiushonak.bank.api.util.ExceptionUtils.new_InvalidConfirmationException;
import static by.vadzimmatsiushonak.bank.api.util.NumberUtils.VERIFICATION_MAX_VALUE;
import static by.vadzimmatsiushonak.bank.api.util.NumberUtils.VERIFICATION_MIN_VALUE;
import static by.vadzimmatsiushonak.bank.api.util.NumberUtils.getRandom;

import by.vadzimmatsiushonak.bank.api.facade.AuthorizationFacade;
import by.vadzimmatsiushonak.bank.api.model.UserConfirmation;
import by.vadzimmatsiushonak.bank.api.model.entity.Customer;
import by.vadzimmatsiushonak.bank.api.model.entity.User;
import by.vadzimmatsiushonak.bank.api.model.entity.auth.Role;
import by.vadzimmatsiushonak.bank.api.model.entity.base.UserStatus;
import by.vadzimmatsiushonak.bank.api.service.CustomerService;
import by.vadzimmatsiushonak.bank.api.service.UserService;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.transaction.Transactional;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@AllArgsConstructor
@Validated
@Slf4j
@Service
public class AuthorizationFacadeImpl implements AuthorizationFacade {

    private final CustomerService customerService;
    private final UserService userServices;

    private final Map<String, UserConfirmation> confirmations = new HashMap<>();

    @Override
    @Transactional
    public String registerCustomer(@NotNull Customer customer) {
        customerService.create(customer);
        User user = new User(customer);
        user.setRole(Role.TECHNICAL_USER);
        userServices.create(user);

        String key = UUID.randomUUID().toString();
        Integer code = getRandom(VERIFICATION_MIN_VALUE, VERIFICATION_MAX_VALUE);
        confirmations.put(key, new UserConfirmation(user.getId(), code));
        log.info("Confirmation code '{}' has been prepared for user with key '{}' and id '{}'", code, key,
            user.getId());

        return key;
    }

    @Override
    @Transactional
    public Boolean confirmCustomer(@NotBlank String key,
        @Min(VERIFICATION_MIN_VALUE) @Max(VERIFICATION_MAX_VALUE) Integer code) {
        UserConfirmation confirmation = confirmations.get(key);
        if (confirmation == null) {
            log.info("Confirmation {} not found or expired", key);
            throw new_ConfirmationNotFoundException(key);
        }
        if (!confirmation.getCode().equals(code)) {
            log.info("Invalid verification code {} for key {}", code, key);
            throw new_InvalidConfirmationException(String.valueOf(code), key);
        }

        confirmations.remove(key);
        User user = userServices.findById(confirmation.getId())
            .orElseThrow(() -> new_EntityNotFoundException("User", confirmation.getId()));
        user.setStatus(UserStatus.ACTIVE);
        log.info("User with key {} has been successfully confirmed", key);
        return true;
    }
}
