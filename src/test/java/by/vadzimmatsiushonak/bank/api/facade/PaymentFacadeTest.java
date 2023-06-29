package by.vadzimmatsiushonak.bank.api.facade;

import by.vadzimmatsiushonak.bank.api.exception.DuplicateException;
import by.vadzimmatsiushonak.bank.api.exception.EntityNotFoundException;
import by.vadzimmatsiushonak.bank.api.exception.InsufficientFundsException;
import by.vadzimmatsiushonak.bank.api.exception.UserNotFoundException;
import by.vadzimmatsiushonak.bank.api.facade.impl.PaymentFacadeImpl;
import by.vadzimmatsiushonak.bank.api.model.dto.request.InitiatePaymentRequest;
import by.vadzimmatsiushonak.bank.api.model.entity.BankAccount;
import by.vadzimmatsiushonak.bank.api.model.entity.BankPayment;
import by.vadzimmatsiushonak.bank.api.model.entity.Customer;
import by.vadzimmatsiushonak.bank.api.service.BankAccountService;
import by.vadzimmatsiushonak.bank.api.service.BankPaymentService;
import by.vadzimmatsiushonak.bank.api.service.CustomerService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static utils.BankPaymentBuilder.buildBankPayment;
import static utils.InitiatePaymentRequestBuilder.buildInitiatePaymentRequest;
import static utils.TestConstants.*;

@ExtendWith(MockitoExtension.class)
public class PaymentFacadeTest {

    @InjectMocks
    private PaymentFacadeImpl facade;

    @Mock
    private BankPaymentService paymentService;
    @Mock
    private BankAccountService accountService;
    @Mock
    private CustomerService customerService;

    @Nested
    public class PaymentFacadeTestInitiatePayment {

        @Test
        public void initiatePayment() {
            InitiatePaymentRequest request = buildInitiatePaymentRequest();

            BankAccount sender = new BankAccount();
            sender.setIban(SENDER);
            sender.setAmount(AMOUNT_BD);
            Customer customer = new Customer();
            customer.setBankAccounts(List.of(sender));

            BankAccount recipient = new BankAccount();
            recipient.setIban(RECIPIENT);
            recipient.setAmount(AMOUNT_BD);

            BankPayment bankPayment = buildBankPayment();
            BankPayment expected = buildBankPayment();
            expected.setId(ID_LONG);

            when(customerService.findByPhoneNumber(PHONENUMBER)).thenReturn(
                    Optional.of(customer));
            when(accountService.findById(RECIPIENT)).thenReturn(Optional.of(recipient));
            when(paymentService.save(bankPayment)).thenReturn(expected);

            BankPayment actual = facade.initiatePayment(PHONENUMBER, request);

            assertEquals(expected, actual);

            verify(customerService).findByPhoneNumber(PHONENUMBER);
            verify(accountService).findById(RECIPIENT);
            verify(paymentService).save(bankPayment);
            verify(accountService, times(2)).save(any());

            sender.setAmount(AMOUNT_BD.subtract(AMOUNT_BD));
            verify(accountService).save(sender);
            recipient.setAmount(AMOUNT_BD.add(AMOUNT_BD));
            verify(accountService).save(recipient);
        }

        @Test
        public void initiatePaymentDuplicateData() {
            InitiatePaymentRequest request = buildInitiatePaymentRequest();
            request.recipientIban = request.senderIban;

            assertThrows(DuplicateException.class,
                    () -> facade.initiatePayment(PHONENUMBER, request));

            verify(customerService, times(0)).findByPhoneNumber(any());
            verify(accountService, times(0)).findById(any());
            verify(paymentService, times(0)).save(any());
            verify(accountService, times(0)).save(any());
        }

        @Test
        public void initiatePaymentAbsentCustomer() {
            InitiatePaymentRequest request = buildInitiatePaymentRequest();

            when(customerService.findByPhoneNumber(PHONENUMBER)).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class,
                    () -> facade.initiatePayment(PHONENUMBER, request));

            verify(customerService).findByPhoneNumber(PHONENUMBER);
            verify(accountService, times(0)).findById(any());
            verify(paymentService, times(0)).save(any());
            verify(accountService, times(0)).save(any());
        }

        @Test
        public void initiatePaymentAbsentSender() {
            InitiatePaymentRequest request = buildInitiatePaymentRequest();

            Customer customer = new Customer();
            customer.setBankAccounts(Collections.emptyList());

            when(customerService.findByPhoneNumber(PHONENUMBER)).thenReturn(
                    Optional.of(customer));

            assertThrows(EntityNotFoundException.class,
                    () -> facade.initiatePayment(PHONENUMBER, request));

            verify(customerService).findByPhoneNumber(PHONENUMBER);
            verify(accountService, times(0)).findById(any());
            verify(paymentService, times(0)).save(any());
            verify(accountService, times(0)).save(any());
        }

        @Test
        public void initiatePaymentAbsentRecipient() {
            InitiatePaymentRequest request = buildInitiatePaymentRequest();

            BankAccount bankAccount = new BankAccount();
            bankAccount.setIban(SENDER);
            Customer customer = new Customer();
            customer.setBankAccounts(List.of(bankAccount));

            when(customerService.findByPhoneNumber(PHONENUMBER)).thenReturn(
                    Optional.of(customer));
            when(accountService.findById(RECIPIENT)).thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class,
                    () -> facade.initiatePayment(PHONENUMBER, request));

            verify(customerService).findByPhoneNumber(PHONENUMBER);
            verify(accountService).findById(RECIPIENT);
            verify(paymentService, times(0)).save(any());
            verify(accountService, times(0)).save(any());
        }

        @Test
        public void initiatePaymentInsufficientSenderFunds() {
            InitiatePaymentRequest request = buildInitiatePaymentRequest();

            BankAccount sender = new BankAccount();
            sender.setIban(SENDER);
            sender.setAmount(new BigDecimal(0));
            Customer customer = new Customer();
            customer.setBankAccounts(List.of(sender));

            BankAccount recipient = new BankAccount();
            recipient.setIban(RECIPIENT);

            when(customerService.findByPhoneNumber(PHONENUMBER)).thenReturn(
                    Optional.of(customer));
            when(accountService.findById(RECIPIENT)).thenReturn(Optional.of(recipient));

            assertThrows(InsufficientFundsException.class,
                    () -> facade.initiatePayment(PHONENUMBER, request));

            verify(customerService).findByPhoneNumber(PHONENUMBER);
            verify(accountService).findById(RECIPIENT);
            verify(paymentService, times(0)).save(any());
            verify(accountService, times(0)).save(any());
        }

    }

}
