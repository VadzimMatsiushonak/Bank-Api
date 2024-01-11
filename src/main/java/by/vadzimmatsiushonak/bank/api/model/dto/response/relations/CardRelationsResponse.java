package by.vadzimmatsiushonak.bank.api.model.dto.response.relations;

import by.vadzimmatsiushonak.bank.api.model.dto.response.AccountResponse;
import by.vadzimmatsiushonak.bank.api.model.dto.response.BankResponse;
import by.vadzimmatsiushonak.bank.api.model.dto.response.CardResponse;

public class CardRelationsResponse extends CardResponse {

    public AccountResponse account;
    public BankResponse bank;

}
