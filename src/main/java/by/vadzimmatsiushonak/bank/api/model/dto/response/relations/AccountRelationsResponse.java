package by.vadzimmatsiushonak.bank.api.model.dto.response.relations;

import by.vadzimmatsiushonak.bank.api.model.dto.response.AccountHolderResponse;
import by.vadzimmatsiushonak.bank.api.model.dto.response.AccountResponse;
import by.vadzimmatsiushonak.bank.api.model.dto.response.BankResponse;

public class AccountRelationsResponse extends AccountResponse {

    public AccountHolderResponse accountHolder;
    public BankResponse bank;

}
