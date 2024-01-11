package by.vadzimmatsiushonak.bank.api.model.dto.response.relations;

import by.vadzimmatsiushonak.bank.api.model.dto.response.AccountResponse;
import by.vadzimmatsiushonak.bank.api.model.dto.response.CardResponse;
import by.vadzimmatsiushonak.bank.api.model.dto.response.TransactionResponse;

public class TransactionRelationsResponse extends TransactionResponse {

    public AccountResponse sender;
    public CardResponse senderCard;
    public AccountResponse recipient;
    public CardResponse recipientCard;

}
