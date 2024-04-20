package by.vadzimmatsiushonak.bank.api.facade;

import static by.vadzimmatsiushonak.bank.api.util.NumberUtils.CONFIRMATION_MAX_VALUE;
import static by.vadzimmatsiushonak.bank.api.util.NumberUtils.CONFIRMATION_MIN_VALUE;

import by.vadzimmatsiushonak.bank.api.model.dto.request.InitiateTransactionRequest;
import by.vadzimmatsiushonak.bank.api.model.pojo.TransactionConfirmation;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface PaymentFacade {

    TransactionConfirmation initiatePayment(@NotBlank String login, @NotNull InitiateTransactionRequest request);

    Long confirmPayment(@NotBlank String key,
        @Min(CONFIRMATION_MIN_VALUE) @Max(CONFIRMATION_MAX_VALUE) Integer code);

    void withdrawUnconfirmedPayment(@NotNull Long transactionId);
}
