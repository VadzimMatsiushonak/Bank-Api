package by.vadzimmatsiushonak.bank.api.facade.impl;

import by.vadzimmatsiushonak.bank.api.exception.DuplicateException;
import by.vadzimmatsiushonak.bank.api.exception.EntityNotFoundException;
import by.vadzimmatsiushonak.bank.api.exception.InsufficientFundsException;
import by.vadzimmatsiushonak.bank.api.exception.UserNotFoundException;
import by.vadzimmatsiushonak.bank.api.facade.PaymentFacade;
import by.vadzimmatsiushonak.bank.api.service.VerificationService;
import by.vadzimmatsiushonak.bank.api.model.UserVerification;
import by.vadzimmatsiushonak.bank.api.model.dto.request.InitiatePaymentRequest;
import by.vadzimmatsiushonak.bank.api.model.dto.response.BankPaymentVerificationResponse;
import by.vadzimmatsiushonak.bank.api.model.entity.BankAccount;
import by.vadzimmatsiushonak.bank.api.model.entity.BankPayment;
import by.vadzimmatsiushonak.bank.api.model.entity.User;
import by.vadzimmatsiushonak.bank.api.model.entity.base.PaymentStatus;
import by.vadzimmatsiushonak.bank.api.service.BankAccountService;
import by.vadzimmatsiushonak.bank.api.service.BankPaymentService;
import by.vadzimmatsiushonak.bank.api.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import static by.vadzimmatsiushonak.bank.api.util.ExceptionUtils.*;
import static by.vadzimmatsiushonak.bank.api.util.NumberUtils.VERIFICATION_MAX_VALUE;
import static by.vadzimmatsiushonak.bank.api.util.NumberUtils.VERIFICATION_MIN_VALUE;

@AllArgsConstructor
@Validated
@Slf4j
@Service
public class PaymentFacadeImpl implements PaymentFacade {

    public final static String PAYMENT_KEY = "P_";
    public final VerificationService verificationService;

    private final BankPaymentService paymentService;
    private final BankAccountService accountService;
    private final UserService userService;

    /**
     * Initiates a payment between two bank accounts.
     * The user initiating the payment is identified by their phone number,
     * and their account is identified using the provided sender IBAN.
     * The recipient account is identified using the provided recipient IBAN.
     *
     * @param phoneNumber initiator phoneNumber
     * @param request     InitiatePaymentRequest containing all information payment the payment
     * @return saved BankPayment entity representing the payment that was initiated.
     * @throws DuplicateException         If the sender and recipient have the same IBAN.
     * @throws UserNotFoundException      If the user cannot be found using the provided phone number.
     * @throws EntityNotFoundException    If the sender or recipient account cannot be found using the provided IBAN.
     * @throws InsufficientFundsException If the sender account does not have sufficient funds to complete the payment.
     */
    @Override
    public BankPaymentVerificationResponse initiatePayment(@NotBlank String phoneNumber,
                                                           @NotNull InitiatePaymentRequest request) {
        if (request.senderIban.equals(request.recipientIban)) {
            throw new_DuplicateException(request.senderIban);
        }

        User user = userService.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new_UserNotFoundException(phoneNumber));

        BankAccount sender = user.getBankAccounts().stream()
                .filter(i -> request.senderIban.equals(i.getIban()))
                .findFirst()
                .orElseThrow(() -> new_EntityNotFoundException("Sender", request.senderIban));

        BankAccount recipient = accountService.findById(request.recipientIban)
                .orElseThrow(
                        () -> new_EntityNotFoundException("Recipient", request.recipientIban));

        BigDecimal senderAmount = sender.getAmount();
        BigDecimal recipientAmount = recipient.getAmount();

        if (senderAmount.compareTo(request.amount) >= 0) {
            sender.setAmount(senderAmount.subtract(request.amount));
            recipient.setAmount(recipientAmount.add(request.amount));

            accountService.update(sender);
            accountService.update(recipient);

            BankAccount account = new BankAccount();
            account.setIban(sender.getIban());

            BankPayment payment = new BankPayment();
            payment.setAmount(request.amount);
            payment.setCurrency(request.currency);
            payment.setBankAccount(account);
            payment.setRecipientBankAccountIban(recipient.getIban());
            payment.setStatus(PaymentStatus.PENDING);

            payment = paymentService.save(payment);
            String code = verificationService.generateCode(payment, PAYMENT_KEY);

            return new BankPaymentVerificationResponse(payment.getId(), code);
        } else {
            throw new_InsufficientFundsException(sender.getIban());
        }
    }

    /**
     * Verifies provided key and code and set the payment status to the ACCEPTED
     *
     * @param key  the verification key
     * @param code the verification code
     * @return the Boolean response indicating whether the verification was successful.
     * @throws EntityNotFoundException If the user cannot be found using the ID associated with the verification key.
     */
    @Override
    @Transactional
    public Boolean confirmPayment(@NotBlank String key,
                                  @Min(VERIFICATION_MIN_VALUE) @Max(VERIFICATION_MAX_VALUE) Integer code) {
        UserVerification verification = (UserVerification) verificationService.verifyCode(key, code);

        BankPayment bankPayment = paymentService.findById(verification.getBaseEntity().getId())
                .orElseThrow(() -> new_EntityNotFoundException("BankPayment", verification.getBaseEntity().getId()));

        bankPayment.setStatus(PaymentStatus.ACCEPTED);

        log.info("User '{}' with key {} successfully confirmed payment", bankPayment.getBankAccount().getUser().getLogin(), key);

        return true;
    }
}
