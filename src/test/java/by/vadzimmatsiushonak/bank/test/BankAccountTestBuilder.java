package by.vadzimmatsiushonak.bank.test;

import by.vadzimmatsiushonak.bank.api.model.entity.BankAccount;

import static by.vadzimmatsiushonak.bank.test.TestConstants.*;

public class BankAccountTestBuilder {


    public static BankAccount buildSenderBankAccount() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(TEST_SENDER_ID);
        bankAccount.setAmount(TEST_BD_AMOUNT);
        bankAccount.setTitle(TEST_SENDER_TITLE);
        return bankAccount;
    }

    public static BankAccount buildRecipientBankAccount() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(TEST_RECIPIENT_ID);
        bankAccount.setAmount(TEST_BD_AMOUNT);
        bankAccount.setTitle(TEST_RECIPIENT_TITLE);
        return bankAccount;
    }

}
