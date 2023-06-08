package by.vadzimmatsiushonak.bank.test;

import by.vadzimmatsiushonak.bank.api.model.dto.request.InitiatePaymentRequest;
import by.vadzimmatsiushonak.bank.api.model.entity.base.Currency;

import java.math.BigDecimal;

import static by.vadzimmatsiushonak.bank.test.TestConstants.*;

public class InitiatePaymentRequestTestBuilder {


    public static InitiatePaymentRequest buildInitiatePaymentRequest() {
        InitiatePaymentRequest initiatePaymentRequest = new InitiatePaymentRequest();
        initiatePaymentRequest.amount = TEST_PAYMENT_AMOUNT;
        initiatePaymentRequest.currency = Currency.USD;
        initiatePaymentRequest.senderBankAccountId = TEST_SENDER_ID;
        initiatePaymentRequest.recipientBankAccountId = TEST_RECIPIENT_ID;
        return initiatePaymentRequest;
    }


}
