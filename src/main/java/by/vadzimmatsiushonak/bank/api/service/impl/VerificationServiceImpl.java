package by.vadzimmatsiushonak.bank.api.service.impl;

import by.vadzimmatsiushonak.bank.api.exception.InvalidVerificationException;
import by.vadzimmatsiushonak.bank.api.exception.VerificationNotFoundException;
import by.vadzimmatsiushonak.bank.api.service.VerificationService;
import by.vadzimmatsiushonak.bank.api.model.Verification;
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

import static by.vadzimmatsiushonak.bank.api.util.ExceptionUtils.new_InvalidVerificationException;
import static by.vadzimmatsiushonak.bank.api.util.ExceptionUtils.new_VerificationNotFoundException;
import static by.vadzimmatsiushonak.bank.api.util.NumberUtils.VERIFICATION_MAX_VALUE;
import static by.vadzimmatsiushonak.bank.api.util.NumberUtils.VERIFICATION_MIN_VALUE;
import static by.vadzimmatsiushonak.bank.api.util.NumberUtils.getRandom;

@AllArgsConstructor
@Validated
@Slf4j
@Service
public class VerificationServiceImpl implements VerificationService {

    private final Cache verificationCache;

    /**
     * Provides key and saves code
     * Sends code to the user device/mail
     *
     * @param metaData  value is the entity data that will be stored in the cache
     * @param prefix the prefix used for the generated key value
     * @return the String response containing the UUID key stored in cache
     */
    @Override
    public String generateCode(@NotNull Map<Object, Object> metaData, String prefix) {
        String key = UUID.randomUUID().toString();
        if (prefix != null) {
            key = prefix.concat(key);
        }
        Integer code = getRandom(VERIFICATION_MIN_VALUE, VERIFICATION_MAX_VALUE);
        verificationCache.put(key, new Verification(code, metaData));
        log.info("Verification code '{}' has been prepared  with key '{}'", code, key);

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
    public Verification verifyCode(@NotBlank String key,
                                   @Min(VERIFICATION_MIN_VALUE) @Max(VERIFICATION_MAX_VALUE) Integer code) {
        Verification verification = this.verificationCache.get(key, Verification.class);

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
