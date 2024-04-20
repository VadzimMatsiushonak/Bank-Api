package by.vadzimmatsiushonak.bank.api.service;

import static by.vadzimmatsiushonak.bank.api.util.NumberUtils.CONFIRMATION_MAX_VALUE;
import static by.vadzimmatsiushonak.bank.api.util.NumberUtils.CONFIRMATION_MIN_VALUE;

import by.vadzimmatsiushonak.bank.api.model.Confirmation;
import java.util.Map;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface ConfirmationService {

    String generateCode(@NotNull Map<Object, Object> metaData, String prefix);

    Confirmation confirmCode(@NotBlank String key,
        @Min(CONFIRMATION_MIN_VALUE) @Max(CONFIRMATION_MAX_VALUE) Integer code);
}
