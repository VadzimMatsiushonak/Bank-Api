package utils;

import by.vadzimmatsiushonak.bank.api.model.dto.request.InitiatePaymentRequest;

import static utils.TestConstants.*;

public class InitiatePaymentRequestBuilder {

    public static InitiatePaymentRequest buildInitiatePaymentRequest() {
        InitiatePaymentRequest request = new InitiatePaymentRequest();
        request.amount = AMOUNT_BD;
        request.currency = CURRENCY;
        request.senderIban = SENDER;
        request.recipientIban = RECIPIENT;
        return request;
    }

}
