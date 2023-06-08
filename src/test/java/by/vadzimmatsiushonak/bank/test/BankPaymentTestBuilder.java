package by.vadzimmatsiushonak.bank.test;

import by.vadzimmatsiushonak.bank.api.model.entity.BankAccount;
import by.vadzimmatsiushonak.bank.api.model.entity.BankPayment;

import static by.vadzimmatsiushonak.bank.api.model.entity.base.Currency.USD;
import static by.vadzimmatsiushonak.bank.api.model.entity.base.PaymentStatus.ACCEPTED;
import static by.vadzimmatsiushonak.bank.test.TestConstants.*;

public class BankPaymentTestBuilder {


    public static BankPayment buildBankPayment() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(TEST_SENDER_ID);

        BankPayment bankPayment = new BankPayment();
        bankPayment.setAmount(TEST_PAYMENT_AMOUNT);
        bankPayment.setCurrency(USD);
        bankPayment.setRecipientBankAccountId(TEST_RECIPIENT_ID);
        bankPayment.setStatus(ACCEPTED);
        bankPayment.setBankAccount(bankAccount);

        return bankPayment;
    }
}
