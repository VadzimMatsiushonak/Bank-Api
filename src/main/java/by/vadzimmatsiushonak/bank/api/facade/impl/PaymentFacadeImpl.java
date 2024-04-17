package by.vadzimmatsiushonak.bank.api.facade.impl;

import static by.vadzimmatsiushonak.bank.api.constant.MetadataConstants.ID;
import static by.vadzimmatsiushonak.bank.api.util.ExceptionUtils.new_DuplicateException;
import static by.vadzimmatsiushonak.bank.api.util.ExceptionUtils.new_EntityNotFoundException;
import static by.vadzimmatsiushonak.bank.api.util.ExceptionUtils.new_InsufficientFundsException;
import static by.vadzimmatsiushonak.bank.api.util.NumberUtils.CONFIRMATION_MAX_VALUE;
import static by.vadzimmatsiushonak.bank.api.util.NumberUtils.CONFIRMATION_MIN_VALUE;
import static java.util.Optional.ofNullable;

import by.vadzimmatsiushonak.bank.api.exception.DuplicateException;
import by.vadzimmatsiushonak.bank.api.exception.EntityNotFoundException;
import by.vadzimmatsiushonak.bank.api.exception.InsufficientFundsException;
import by.vadzimmatsiushonak.bank.api.exception.UserNotFoundException;
import by.vadzimmatsiushonak.bank.api.facade.PaymentFacade;
import by.vadzimmatsiushonak.bank.api.model.Confirmation;
import by.vadzimmatsiushonak.bank.api.model.dto.request.InitiateTransactionRequest;
import by.vadzimmatsiushonak.bank.api.model.entity.Account;
import by.vadzimmatsiushonak.bank.api.model.entity.AccountHolder;
import by.vadzimmatsiushonak.bank.api.model.entity.Transaction;
import by.vadzimmatsiushonak.bank.api.model.entity.User;
import by.vadzimmatsiushonak.bank.api.model.entity.base.TransactionCategory;
import by.vadzimmatsiushonak.bank.api.model.entity.base.TransactionStatus;
import by.vadzimmatsiushonak.bank.api.model.entity.base.TransactionType;
import by.vadzimmatsiushonak.bank.api.service.AccountService;
import by.vadzimmatsiushonak.bank.api.service.ConfirmationService;
import by.vadzimmatsiushonak.bank.api.service.TransactionService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@AllArgsConstructor
@Validated
@Slf4j
@Service
public class PaymentFacadeImpl implements PaymentFacade {

    public final static String PAYMENT_KEY = "P_";

    public final ConfirmationService confirmationService;

    private final TransactionService transactionService;
    private final AccountService accountService;

    /**
     * Initiates a payment between two bank accounts. The user initiating the payment is identified by their login, and
     * their account is identified using the provided sender IBAN. The recipient account is identified using the
     * provided recipient IBAN.
     *
     * @param login   initiator login
     * @param request InitiatePaymentRequest containing all information payment the payment
     * @return the String response containing the UUID key for the confirmation request.
     * @throws DuplicateException         If the sender and recipient have the same IBAN.
     * @throws UserNotFoundException      If the user cannot be found using the provided login.
     * @throws EntityNotFoundException    If the sender or recipient account cannot be found using the provided IBAN.
     * @throws InsufficientFundsException If the sender account does not have sufficient funds to complete the payment.
     */
    @Override
    @Transactional
    public String initiatePayment(@NotBlank String login, @NotNull InitiateTransactionRequest request) {
        verifyRequestData(request);

        Account sender = getAccount(request.senderIban);
        verifySenderAccount(login, request.senderIban, sender);

        Account recipient = getAccount(request.recipientIban);

        BigDecimal sentAmount = request.amount;
        if (hasSufficientFunds(sender, sentAmount)) {
            BigDecimal feePercent = calculateChargeFeePercent(sender.getBank().getChargeFee());
            BigDecimal fee = sentAmount.multiply(feePercent);
            BigDecimal receivedAmount = sentAmount.subtract(fee);

            updateBalancesAmount(sender, sentAmount, recipient, receivedAmount);
            updateAccounts(sender, recipient);

            Transaction payment = buildTransaction(request, sentAmount, sender.getBank().getChargeFee(), fee, sender, recipient);

            payment = transactionService.save(payment);
            // TODO delete transaction after some time, if not confirmed

            return generateConfirmationCode(payment);
            // TODO add payment id to metadata response to get transaction id after initiation
        } else {
            throw new_InsufficientFundsException(sender.getIban());
        }
    }

    protected void verifyRequestData(InitiateTransactionRequest request) {
        if (request.senderIban.equals(request.recipientIban)) {
            throw new_DuplicateException(request.senderIban);
        }
    }

    protected Account getAccount(String iban) {
        return accountService.findByIban(iban)
            .orElseThrow(() -> new_EntityNotFoundException("Account", iban));
    }

    protected void verifySenderAccount(String login, String senderIban, Account sender) {
        String senderLogin = ofNullable(sender)
            .map(Account::getAccountHolder)
            .map(AccountHolder::getUser)
            .map(User::getLogin)
            // TODO: better exception like "sender doesn't have login"
            .orElseThrow(() -> new_EntityNotFoundException("Sender", senderIban));
        if (!login.equalsIgnoreCase(senderLogin)) {
            // TODO: better exception like "incorrect sender iban provided"
            throw new_EntityNotFoundException("Sender", senderIban);
        }
    }

    protected boolean hasSufficientFunds(Account account, BigDecimal amount) {
        return account.getAmount().compareTo(amount) >= 0;
    }

    protected BigDecimal calculateChargeFeePercent(BigDecimal chargeFee) {
        if (chargeFee != null && chargeFee.compareTo(BigDecimal.ZERO) > 0) {
            return chargeFee.divide(new BigDecimal(100), 2, RoundingMode.UP);
        }
        return BigDecimal.ZERO;
    }

    protected void updateBalancesAmount(Account sender, BigDecimal sentAmount, Account recipient,
        BigDecimal receivedAmount) {
        sender.setAmount(sender.getAmount().subtract(sentAmount));
        recipient.setAmount(recipient.getAmount().add(receivedAmount));
    }

    protected void updateAccounts(Account sender, Account recipient) {
        accountService.update(sender);
        accountService.update(recipient);
    }

    protected Transaction buildTransaction(InitiateTransactionRequest request, BigDecimal sentAmount,
        BigDecimal feePercent, BigDecimal fee, Account sender, Account recipient) {
        Transaction payment = new Transaction();
        payment.setAmount(sentAmount);
        payment.setFeePercent(feePercent);
        payment.setFeeAmount(fee);
        payment.setCurrency(request.currency);
        payment.setSender(sender);
        payment.setRecipient(recipient);
        payment.setType(TransactionType.DEBIT);
        payment.setCategory(TransactionCategory.PAYMENT);
        payment.setStatus(TransactionStatus.INITIATED);
        return payment;
    }

    protected String generateConfirmationCode(Transaction payment) {
        return confirmationService.generateCode(Map.of(ID, payment.getId()), PAYMENT_KEY);
    }

    /**
     * Confirms provided key and code and set the payment status to the INITIATED
     *
     * @param key  the confirmation key
     * @param code the confirmation code
     * @return the Long value  with transaction id.
     * @throws EntityNotFoundException If the user cannot be found using the ID associated with the confirmation key.
     */
    @Override
    @Transactional
    public Long confirmPayment(@NotBlank String key,
        @Min(CONFIRMATION_MIN_VALUE) @Max(CONFIRMATION_MAX_VALUE) Integer code) {
        Confirmation confirmation = confirmationService.confirmCode(key, code);

        Long confirmTransactionId = (Long) confirmation.getMetaData().get(ID);

        completeTransaction(confirmTransactionId);

        log.info("Payment with id '{}' and key {} successfully confirmed", confirmTransactionId, key);

        return confirmTransactionId;
    }

    protected void completeTransaction(Long confirmTransactionId) {
        Transaction transaction = transactionService.findById(confirmTransactionId)
            .orElseThrow(() -> new_EntityNotFoundException("Transaction", confirmTransactionId));

        transaction.setStatus(TransactionStatus.COMPLETED);
    }
}
