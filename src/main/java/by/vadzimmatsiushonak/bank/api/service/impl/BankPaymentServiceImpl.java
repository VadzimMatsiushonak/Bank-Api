package by.vadzimmatsiushonak.bank.api.service.impl;

import static by.vadzimmatsiushonak.bank.api.util.ExceptionUtils.new_EntityNotFoundException;
import static by.vadzimmatsiushonak.bank.api.util.ExceptionUtils.new_InsufficientFundsException;
import static by.vadzimmatsiushonak.bank.api.util.ExceptionUtils.new_WrongDataException;

import by.vadzimmatsiushonak.bank.api.model.dto.request.InitiatePaymentRequest;
import by.vadzimmatsiushonak.bank.api.model.entity.BankAccount;
import by.vadzimmatsiushonak.bank.api.model.entity.BankPayment;
import by.vadzimmatsiushonak.bank.api.model.entity.base.PaymentStatus;
import by.vadzimmatsiushonak.bank.api.repository.BankAccountRepository;
import by.vadzimmatsiushonak.bank.api.repository.BankPaymentRepository;
import by.vadzimmatsiushonak.bank.api.service.BankPaymentService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@AllArgsConstructor
@Validated
@Slf4j
@Service
public class BankPaymentServiceImpl implements BankPaymentService {

    private final BankPaymentRepository repository;

    private final BankAccountRepository bankAccountRepository;

    @Override
    public BankPayment create(@NotNull BankPayment bankPayment) {
        log.info("BankPaymentServiceImpl create {}", bankPayment);
        bankPayment.setId(null);

        return repository.save(bankPayment);
    }

    @Override
    public Optional<BankPayment> findById(@NotNull Long id) {
        log.info("BankPaymentServiceImpl findById {}", id);

        return repository.findById(id);
    }

    @Override
    public List<BankPayment> findAll() {
        log.info("BankPaymentServiceImpl findAll");

        return repository.findAll();
    }

    @Override
    public void update(@NotNull BankPayment bankPayment) {
        log.info("BankPaymentServiceImpl update {}", bankPayment);

        Objects.requireNonNull(bankPayment.getId());
        repository.save(bankPayment);
    }

    @Override
    public void delete(@NotNull BankPayment bankPayment) {
        log.info("BankPaymentServiceImpl delete {}", bankPayment);

        repository.delete(bankPayment);
    }

    @Override
    public void deleteById(@NotNull Long id) {
        log.info("BankPaymentServiceImpl deleteById {}", id);

        repository.deleteById(id);
    }

    @Override
    public BankPayment initiatePayment(@NotNull final InitiatePaymentRequest request) {
        if (request.senderBankAccountId.equals(request.recipientBankAccountId)) {
            throw new_WrongDataException(request.senderBankAccountId);
        }

        BankAccount senderBankAccount = bankAccountRepository.findById(request.senderBankAccountId).orElseThrow(
            () -> new_EntityNotFoundException("Sender", request.senderBankAccountId));
        BankAccount recipientBankAccount = bankAccountRepository.findById(request.recipientBankAccountId).orElseThrow(
            () -> new_EntityNotFoundException("Recipient", request.recipientBankAccountId));

        BigDecimal senderAmount = senderBankAccount.getAmount();
        BigDecimal recipientAmount = recipientBankAccount.getAmount();

        if (senderAmount.compareTo(request.amount) >= 0) {
            senderBankAccount.setAmount(senderAmount.subtract(request.amount));
            recipientBankAccount.setAmount(recipientAmount.add(request.amount));

            bankAccountRepository.save(senderBankAccount);
            bankAccountRepository.save(recipientBankAccount);

            BankAccount bankAccount = new BankAccount();
            bankAccount.setId(request.senderBankAccountId);

            BankPayment bankPayment = new BankPayment();
            bankPayment.setAmount(request.amount);
            bankPayment.setCurrency(request.currency);
            bankPayment.setBankAccount(bankAccount);
            bankPayment.setRecipientBankAccountId(request.recipientBankAccountId);
            bankPayment.setStatus(PaymentStatus.ACCEPTED);

            return repository.save(bankPayment);
        } else {
            throw new_InsufficientFundsException(request.senderBankAccountId);
        }
    }

}
