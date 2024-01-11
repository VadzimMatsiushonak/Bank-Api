package utils;

import by.vadzimmatsiushonak.bank.api.model.dto.request.InitiateTransactionRequest;
import by.vadzimmatsiushonak.bank.api.model.entity.Account;
import by.vadzimmatsiushonak.bank.api.model.entity.Transaction;
import by.vadzimmatsiushonak.bank.api.model.entity.base.TransactionCategory;
import by.vadzimmatsiushonak.bank.api.model.entity.base.TransactionStatus;
import by.vadzimmatsiushonak.bank.api.model.entity.base.TransactionType;

import static utils.TestConstants.*;

public class TransactionBuilder {

    public static Transaction buildTransaction() {
        Account sender = new Account();
        sender.setIban(SENDER);
        Account recipient = new Account();
        recipient.setIban(RECIPIENT);

        Transaction payment = new Transaction();
        payment.setAmount(AMOUNT_BD);
        payment.setCurrency(CURRENCY);
        payment.setSender(sender);
        payment.setRecipient(recipient);
        payment.setType(TransactionType.DEBIT);
        payment.setCategory(TransactionCategory.PAYMENT);
        payment.setStatus(TransactionStatus.INITIATED);

        return payment;
    }

    public static InitiateTransactionRequest buildInitiateTransactionRequest() {
        InitiateTransactionRequest request = new InitiateTransactionRequest();
        request.amount = AMOUNT_BD;
        request.currency = CURRENCY;
        request.senderIban = SENDER;
        request.recipientIban = RECIPIENT;
        return request;
    }

}
