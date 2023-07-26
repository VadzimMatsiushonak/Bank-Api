package by.vadzimmatsiushonak.bank.api.service;

import by.vadzimmatsiushonak.bank.api.model.Verification;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.Map;

import static by.vadzimmatsiushonak.bank.api.util.NumberUtils.VERIFICATION_MAX_VALUE;
import static by.vadzimmatsiushonak.bank.api.util.NumberUtils.VERIFICATION_MIN_VALUE;

public interface VerificationService {

    String generateCode(@NotNull Map<Object, Object> metaData, String prefix);

    Verification verifyCode(@NotBlank String key,
                            @Min(VERIFICATION_MIN_VALUE) @Max(VERIFICATION_MAX_VALUE) Integer code);
}
