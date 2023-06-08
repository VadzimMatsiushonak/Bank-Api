package by.vadzimmatsiushonak.bank.api.service;

import by.vadzimmatsiushonak.bank.api.model.dto.request.InitiatePaymentRequest;
import by.vadzimmatsiushonak.bank.api.model.entity.BankAccount;
import by.vadzimmatsiushonak.bank.api.model.entity.BankPayment;
import by.vadzimmatsiushonak.bank.api.repository.BankAccountRepository;
import by.vadzimmatsiushonak.bank.api.repository.BankPaymentRepository;
import by.vadzimmatsiushonak.bank.api.service.impl.BankPaymentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static by.vadzimmatsiushonak.bank.test.BankAccountTestBuilder.buildRecipientBankAccount;
import static by.vadzimmatsiushonak.bank.test.BankAccountTestBuilder.buildSenderBankAccount;
import static by.vadzimmatsiushonak.bank.test.BankPaymentTestBuilder.buildBankPayment;
import static by.vadzimmatsiushonak.bank.test.InitiatePaymentRequestTestBuilder.buildInitiatePaymentRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BankPaymentServiceTest {

    @InjectMocks
    private BankPaymentServiceImpl bankPaymentService;

    @Mock
    private BankPaymentRepository bankPaymentRepository;
    @Mock
    private BankAccountRepository bankAccountRepository;

    @Test
    public void initiatePaymentTest() {
        InitiatePaymentRequest request = buildInitiatePaymentRequest();
        BankPayment payment = buildBankPayment();

        BankAccount expectedSender = buildSenderBankAccount();
        BankAccount expectedRecipient = buildRecipientBankAccount();
        expectedSender.setAmount(expectedSender.getAmount().subtract(request.amount));
        expectedRecipient.setAmount(expectedRecipient.getAmount().add(request.amount));


        BankPayment expectedPayment = buildBankPayment();
        expectedPayment.setId(1L);

        when(bankAccountRepository.findById(1l))
                .thenReturn(Optional.of(buildSenderBankAccount()));
        when(bankAccountRepository.findById(2l))
                .thenReturn(Optional.of(buildRecipientBankAccount()));
        when(bankPaymentRepository.save(payment)).thenReturn(expectedPayment);


        BankPayment actualPayment = bankPaymentService.initiatePayment(request);
        assertEquals(expectedPayment, actualPayment);



        verify(bankAccountRepository, times(1)).save(expectedSender);
        verify(bankAccountRepository, times(1)).save(expectedRecipient);
        verify(bankAccountRepository, times(2)).save(any());
        verify(bankPaymentRepository, times(1)).save(payment);

        expectedSender.equals(expectedRecipient);
    }

}
