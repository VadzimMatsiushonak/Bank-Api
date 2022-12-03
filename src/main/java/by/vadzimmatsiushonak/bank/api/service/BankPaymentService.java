package by.vadzimmatsiushonak.bank.api.service;

import by.vadzimmatsiushonak.bank.api.model.dto.request.InitiatePaymentRequest;
import by.vadzimmatsiushonak.bank.api.model.entity.BankPayment;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface BankPaymentService {
    BankPayment create(@NotNull BankPayment bankPayment);

    Optional<BankPayment> findById(@NotNull Long id);

    List<BankPayment> findAll();

    void update(@NotNull BankPayment bankPayment);

    void delete(@NotNull BankPayment bankPayment);

    void deleteById(@NotNull Long id);

    BankPayment initiatePayment(@NotNull InitiatePaymentRequest request);
}
