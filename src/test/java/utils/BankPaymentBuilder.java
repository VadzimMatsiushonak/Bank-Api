package utils;

import by.vadzimmatsiushonak.bank.api.model.entity.BankAccount;
import by.vadzimmatsiushonak.bank.api.model.entity.BankPayment;
import by.vadzimmatsiushonak.bank.api.model.entity.base.PaymentStatus;

import static utils.TestConstants.*;

public class BankPaymentBuilder {

    public static BankPayment buildBankPayment() {
        BankAccount account = new BankAccount();
        account.setIban(SENDER);

        BankPayment payment = new BankPayment();
        payment.setAmount(AMOUNT_BD);
        payment.setCurrency(CURRENCY);
        payment.setBankAccount(account);
        payment.setRecipientBankAccountIban(RECIPIENT);
        payment.setStatus(PaymentStatus.ACCEPTED);

        return payment;
    }

}
