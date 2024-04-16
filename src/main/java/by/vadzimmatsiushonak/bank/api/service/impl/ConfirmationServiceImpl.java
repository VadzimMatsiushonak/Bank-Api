package by.vadzimmatsiushonak.bank.api.service.impl;

import by.vadzimmatsiushonak.bank.api.exception.ConfirmationNotFoundException;
import by.vadzimmatsiushonak.bank.api.exception.InvalidConfirmationException;
import by.vadzimmatsiushonak.bank.api.model.Confirmation;
import by.vadzimmatsiushonak.bank.api.service.ConfirmationService;
import by.vadzimmatsiushonak.bank.api.util.NumberUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.UUID;

import static by.vadzimmatsiushonak.bank.api.util.ExceptionUtils.new_ConfirmationNotFoundException;
import static by.vadzimmatsiushonak.bank.api.util.ExceptionUtils.new_InvalidConfirmationException;
import static by.vadzimmatsiushonak.bank.api.util.NumberUtils.CONFIRMATION_MAX_VALUE;
import static by.vadzimmatsiushonak.bank.api.util.NumberUtils.CONFIRMATION_MIN_VALUE;

@AllArgsConstructor
@Validated
@Slf4j
@Service
public class ConfirmationServiceImpl implements ConfirmationService {

    private final Cache confirmationCache;
    private final NumberUtils numberUtils;

    /**
     * Provides key and saves code
     * Sends code to the user device/mail
     *
     * @param metaData value is the entity data that will be stored in the cache
     * @param prefix   the prefix used for the generated key value
     * @return the String response containing the UUID key stored in cache
     */
    @Override
    public String generateCode(@NotNull Map<Object, Object> metaData, String prefix) {
        String key = UUID.randomUUID().toString();
        if (prefix != null) {
            key = prefix.concat(key);
        }
        Integer code = numberUtils.getRandom(CONFIRMATION_MIN_VALUE, CONFIRMATION_MAX_VALUE);
        confirmationCache.put(key, new Confirmation(code, metaData));
        log.info("Confirmation code '{}' has been prepared  with key '{}'", code, key);

        return key;
    }


    /**
     * Confirms provided key and code with the data in the cache
     *
     * @param key  the confirmation key
     * @param code the confirmation code
     * @return the Confirmation response containing information about the confirmed user
     * @throws ConfirmationNotFoundException If the confirmation key cannot be found or has expired.
     * @throws InvalidConfirmationException  If the provided confirmation code is invalid.
     */
    @Override
    public Confirmation confirmCode(@NotBlank String key,
                                    @Min(CONFIRMATION_MIN_VALUE) @Max(CONFIRMATION_MAX_VALUE) Integer code) {
        Confirmation confirmation = this.confirmationCache.get(key, Confirmation.class);

        if (confirmation == null) {
            log.info("Confirmation {} not found or expired", key);
            throw new_ConfirmationNotFoundException(key);
        }

        if (!confirmation.getCode().equals(code)) {
            log.info("Invalid confirmation code {} for key {}", code, key);
            throw new_InvalidConfirmationException(String.valueOf(code), key);
        }

        this.confirmationCache.evictIfPresent(key);

        return confirmation;
    }
}
