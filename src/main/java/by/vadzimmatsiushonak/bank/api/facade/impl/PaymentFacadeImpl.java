package by.vadzimmatsiushonak.bank.api.facade.impl;

import by.vadzimmatsiushonak.bank.api.exception.DuplicateException;
import by.vadzimmatsiushonak.bank.api.exception.EntityNotFoundException;
import by.vadzimmatsiushonak.bank.api.exception.InsufficientFundsException;
import by.vadzimmatsiushonak.bank.api.exception.UserNotFoundException;
import by.vadzimmatsiushonak.bank.api.facade.PaymentFacade;
import by.vadzimmatsiushonak.bank.api.model.dto.request.InitiatePaymentRequest;
import by.vadzimmatsiushonak.bank.api.model.entity.BankAccount;
import by.vadzimmatsiushonak.bank.api.model.entity.BankPayment;
import by.vadzimmatsiushonak.bank.api.model.entity.Customer;
import by.vadzimmatsiushonak.bank.api.model.entity.base.PaymentStatus;
import by.vadzimmatsiushonak.bank.api.repository.BankAccountRepository;
import by.vadzimmatsiushonak.bank.api.repository.BankPaymentRepository;
import by.vadzimmatsiushonak.bank.api.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import static by.vadzimmatsiushonak.bank.api.util.ExceptionUtils.*;

@AllArgsConstructor
@Validated
@Slf4j
@Service
public class PaymentFacadeImpl implements PaymentFacade {

    private final BankPaymentRepository paymentRepository;
    private final BankAccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    /**
     * Initiates a payment between two bank accounts.
     * The customer initiating the payment is identified by their phone number,
     * and their account is identified using the provided sender IBAN.
     * The recipient account is identified using the provided recipient IBAN.
     *
     * @param phoneNumber initiator phoneNumber
     * @param request     InitiatePaymentRequest containing all information payment the payment
     * @return saved BankPayment entity representing the payment that was initiated.
     * @throws DuplicateException         If the sender and recipient have the same IBAN.
     * @throws UserNotFoundException      If the customer cannot be found using the provided phone number.
     * @throws EntityNotFoundException    If the sender or recipient account cannot be found using the provided IBAN.
     * @throws InsufficientFundsException If the sender account does not have sufficient funds to complete the payment.
     */
    @Override
    public BankPayment initiatePayment(@NotBlank String phoneNumber,
                                       @NotNull InitiatePaymentRequest request) {
        if (request.senderIban.equals(request.recipientIban)) {
            throw new_DuplicateException(request.senderIban);
        }

        Customer customer = customerRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new_UserNotFoundException(phoneNumber));

        BankAccount sender = customer.getBankAccounts().stream()
                .filter(i -> request.senderIban.equals(i.getIban()))
                .findFirst()
                .orElseThrow(() -> new_EntityNotFoundException("BankAccount", request.senderIban));

        BankAccount recipient = accountRepository.findByIban(request.recipientIban)
                .orElseThrow(
                        () -> new_EntityNotFoundException("Recipient", request.recipientIban));

        BigDecimal senderAmount = sender.getAmount();
        BigDecimal recipientAmount = recipient.getAmount();

        if (senderAmount.compareTo(request.amount) >= 0) {
            sender.setAmount(senderAmount.subtract(request.amount));
            recipient.setAmount(recipientAmount.add(request.amount));

            accountRepository.save(sender);
            accountRepository.save(recipient);

            BankAccount account = new BankAccount();
            account.setId(sender.getId());

            BankPayment payment = new BankPayment();
            payment.setAmount(request.amount);
            payment.setCurrency(request.currency);
            payment.setBankAccount(account);
            payment.setRecipientBankAccountId(recipient.getId());
            payment.setStatus(PaymentStatus.ACCEPTED);

            return paymentRepository.save(payment);
        } else {
            throw new_InsufficientFundsException(sender.getId());
        }
    }
}
