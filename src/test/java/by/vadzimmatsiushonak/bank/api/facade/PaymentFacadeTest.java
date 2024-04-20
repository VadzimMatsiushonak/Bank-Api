package by.vadzimmatsiushonak.bank.api.facade;

import static by.vadzimmatsiushonak.bank.api.facade.impl.PaymentFacadeImpl.PAYMENT_KEY;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static utils.TestConstants.AMOUNT_BD;
import static utils.TestConstants.CODE_INT;
import static utils.TestConstants.EMPTY_AMOUNT_BD;
import static utils.TestConstants.ID;
import static utils.TestConstants.ID_LONG;
import static utils.TestConstants.KEY;
import static utils.TestConstants.LOGIN;
import static utils.TestConstants.RECIPIENT;
import static utils.TestConstants.SENDER;
import static utils.TestConstants.WRONG_LOGIN;
import static utils.TransactionBuilder.buildInitiateTransactionRequest;
import static utils.TransactionBuilder.buildTransaction;

import by.vadzimmatsiushonak.bank.api.exception.DuplicateException;
import by.vadzimmatsiushonak.bank.api.exception.EntityNotFoundException;
import by.vadzimmatsiushonak.bank.api.exception.InsufficientFundsException;
import by.vadzimmatsiushonak.bank.api.facade.impl.PaymentFacadeImpl;
import by.vadzimmatsiushonak.bank.api.model.Confirmation;
import by.vadzimmatsiushonak.bank.api.model.dto.request.InitiateTransactionRequest;
import by.vadzimmatsiushonak.bank.api.model.entity.Account;
import by.vadzimmatsiushonak.bank.api.model.entity.AccountHolder;
import by.vadzimmatsiushonak.bank.api.model.entity.Bank;
import by.vadzimmatsiushonak.bank.api.model.entity.Transaction;
import by.vadzimmatsiushonak.bank.api.model.entity.User;
import by.vadzimmatsiushonak.bank.api.model.entity.base.TransactionStatus;
import by.vadzimmatsiushonak.bank.api.model.pojo.TransactionConfirmation;
import by.vadzimmatsiushonak.bank.api.service.AccountService;
import by.vadzimmatsiushonak.bank.api.service.ConfirmationService;
import by.vadzimmatsiushonak.bank.api.service.TransactionService;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PaymentFacadeTest {

    @InjectMocks
    private PaymentFacadeImpl facade;

    @Mock
    private ConfirmationService confirmationService;
    @Mock
    private TransactionService transactionService;
    @Mock
    private AccountService accountService;

    @Nested
    public class PaymentFacadeTestInitiatePayment {

        @Test
        public void initiatePayment() {
            InitiateTransactionRequest request = buildInitiateTransactionRequest();

            User user = new User();
            user.setLogin(LOGIN);
            AccountHolder accountHolder = new AccountHolder();
            accountHolder.setUser(user);
            Bank bank = new Bank();
            bank.setChargeFeePercent(BigDecimal.ZERO);
            Account sender = new Account();
            sender.setBank(bank);
            sender.setIban(SENDER);
            sender.setAmount(AMOUNT_BD);
            sender.setAccountHolder(accountHolder);

            Account recipient = new Account();
            recipient.setIban(RECIPIENT);
            recipient.setAmount(AMOUNT_BD);

            Transaction transaction = buildTransaction();
            transaction.setStatus(TransactionStatus.INITIATED);
            transaction.setActualAmount(AMOUNT_BD);
            transaction.setFeePercent(BigDecimal.ZERO);
            transaction.setStatus(TransactionStatus.INITIATED);
            Transaction expected = buildTransaction();
            expected.setId(ID_LONG);
            expected.setStatus(TransactionStatus.INITIATED);
            expected.setActualAmount(AMOUNT_BD);
            expected.setFeePercent(BigDecimal.ZERO);
            String expectedKey = KEY;

            TransactionConfirmation expectedConfirmation = new TransactionConfirmation(expectedKey, ID_LONG);

            when(accountService.findByIban(SENDER)).thenReturn(Optional.of(sender));
            when(accountService.findByIban(RECIPIENT)).thenReturn(Optional.of(recipient));
            when(transactionService.save(transaction)).thenReturn(expected);
            when(confirmationService.generateCode(Map.of(ID, ID_LONG), PAYMENT_KEY)).thenReturn(expectedKey);

            TransactionConfirmation actualConfirmation = facade.initiatePayment(LOGIN, request);

            assertEquals(expectedConfirmation, actualConfirmation);

            verify(accountService).findByIban(SENDER);
            verify(accountService).findByIban(RECIPIENT);
            verify(transactionService).save(transaction);

            sender.setAmount(AMOUNT_BD.subtract(AMOUNT_BD));
        }

        @Test
        public void initiatePaymentDuplicateData() {
            InitiateTransactionRequest request = buildInitiateTransactionRequest();
            request.recipientIban = request.senderIban;

            assertThrows(DuplicateException.class,
                () -> facade.initiatePayment(LOGIN, request));

            verify(accountService, times(0)).findByIban(any());
            verify(transactionService, times(0)).save(any());
            verify(accountService, times(0)).update(any());
        }

        @Test
        public void initiatePaymentAbsentSender() {
            InitiateTransactionRequest request = buildInitiateTransactionRequest();

            when(accountService.findByIban(any())).thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class,
                () -> facade.initiatePayment(LOGIN, request));

            verify(accountService).findByIban(SENDER);
            verify(accountService, times(1)).findByIban(any());
            verify(transactionService, times(0)).save(any());
            verify(accountService, times(0)).update(any());
        }

        @Test
        public void initiatePaymentWrongLogin() {
            InitiateTransactionRequest request = buildInitiateTransactionRequest();

            User user = new User();
            user.setLogin(WRONG_LOGIN);
            AccountHolder accountHolder = new AccountHolder();
            accountHolder.setUser(user);
            Account sender = new Account();
            sender.setIban(SENDER);
            sender.setAccountHolder(accountHolder);

            when(accountService.findByIban(SENDER)).thenReturn(Optional.of(sender));

            assertThrows(EntityNotFoundException.class,
                () -> facade.initiatePayment(LOGIN, request));

            verify(accountService).findByIban(SENDER);
            verify(accountService, times(1)).findByIban(any());
            verify(transactionService, times(0)).save(any());
            verify(accountService, times(0)).update(any());
        }

        @Test
        public void initiatePaymentAbsentRecipient() {
            InitiateTransactionRequest request = buildInitiateTransactionRequest();

            User user = new User();
            user.setLogin(LOGIN);
            AccountHolder accountHolder = new AccountHolder();
            accountHolder.setUser(user);
            Account sender = new Account();
            sender.setIban(SENDER);
            sender.setAccountHolder(accountHolder);

            when(accountService.findByIban(SENDER)).thenReturn(Optional.of(sender));
            when(accountService.findByIban(RECIPIENT)).thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class,
                () -> facade.initiatePayment(LOGIN, request));

            verify(accountService).findByIban(SENDER);
            verify(accountService).findByIban(RECIPIENT);
            verify(accountService, times(2)).findByIban(any());
            verify(transactionService, times(0)).save(any());
            verify(accountService, times(0)).update(any());
        }

        @Test
        public void initiatePaymentInsufficientSenderFunds() {
            InitiateTransactionRequest request = buildInitiateTransactionRequest();

            User user = new User();
            user.setLogin(LOGIN);
            AccountHolder accountHolder = new AccountHolder();
            accountHolder.setUser(user);
            Account sender = new Account();
            sender.setIban(SENDER);
            sender.setAmount(EMPTY_AMOUNT_BD);
            sender.setAccountHolder(accountHolder);

            Account recipient = new Account();
            recipient.setIban(RECIPIENT);

            when(accountService.findByIban(SENDER)).thenReturn(Optional.of(sender));
            when(accountService.findByIban(RECIPIENT)).thenReturn(Optional.of(recipient));

            assertThrows(InsufficientFundsException.class,
                () -> facade.initiatePayment(LOGIN, request));

            verify(accountService).findByIban(SENDER);
            verify(accountService).findByIban(RECIPIENT);
            verify(accountService, times(2)).findByIban(any());
            verify(transactionService, times(0)).save(any());
            verify(accountService, times(0)).update(any());
        }

    }

    @Nested
    public class PaymentFacadeTestConfirmPayment {

        @Test
        public void confirmPayment() {
            Confirmation confirmation = new Confirmation(CODE_INT, Map.of(ID, ID_LONG));
            Account recipient = new Account();
            recipient.setIban(RECIPIENT);
            recipient.setAmount(AMOUNT_BD);
            Transaction expected = new Transaction();
            expected.setId(ID_LONG);
            expected.setStatus(TransactionStatus.COMPLETED);
            expected.setRecipient(recipient);
            expected.setAmount(AMOUNT_BD);
            expected.setActualAmount(AMOUNT_BD);

            when(confirmationService.confirmCode(KEY, CODE_INT)).thenReturn(confirmation);
            when(transactionService.findById(ID_LONG)).thenReturn(Optional.of(expected));

            Long actualId = facade.confirmPayment(KEY, CODE_INT);

            assertAll(
                () -> assertEquals(TransactionStatus.COMPLETED, expected.getStatus()),
                () -> assertEquals(ID_LONG, actualId)
            );

            verify(transactionService).findById(ID_LONG);
        }

        @Test
        public void confirmPaymentTransactionNotFound() {
            Confirmation confirmation = new Confirmation(CODE_INT, Map.of(ID, ID_LONG));

            when(confirmationService.confirmCode(KEY, CODE_INT)).thenReturn(confirmation);
            when(transactionService.findById(ID_LONG)).thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class,
                () -> facade.confirmPayment(KEY, CODE_INT));

            verify(transactionService).findById(ID_LONG);
        }

    }

}
