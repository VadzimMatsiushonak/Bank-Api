package by.vadzimmatsiushonak.bank.api.facade;

import by.vadzimmatsiushonak.bank.api.model.dto.request.InitiatePaymentRequest;
import by.vadzimmatsiushonak.bank.api.model.entity.BankPayment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface PaymentFacade {

    BankPayment initiatePayment(@NotBlank String phoneNumber,
                                @NotNull InitiatePaymentRequest request);

}
