package by.vadzimmatsiushonak.bank.api.facade;

import by.vadzimmatsiushonak.bank.api.model.dto.request.InitiatePaymentRequest;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static by.vadzimmatsiushonak.bank.api.util.NumberUtils.CONFIRMATION_MAX_VALUE;
import static by.vadzimmatsiushonak.bank.api.util.NumberUtils.CONFIRMATION_MIN_VALUE;

public interface PaymentFacade {

    String initiatePayment(@NotBlank String phoneNumber, @NotNull InitiatePaymentRequest request);

    Boolean confirmPayment (@NotBlank String key,
                            @Min(CONFIRMATION_MIN_VALUE) @Max(CONFIRMATION_MAX_VALUE) Integer code);

}
