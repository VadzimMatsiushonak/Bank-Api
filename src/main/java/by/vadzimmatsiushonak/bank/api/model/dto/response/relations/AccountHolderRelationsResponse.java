package by.vadzimmatsiushonak.bank.api.model.dto.response.relations;

import by.vadzimmatsiushonak.bank.api.model.dto.response.AccountHolderResponse;
import by.vadzimmatsiushonak.bank.api.model.dto.response.AccountResponse;
import by.vadzimmatsiushonak.bank.api.model.dto.response.BankResponse;
import by.vadzimmatsiushonak.bank.api.model.dto.response.UserResponse;

import java.util.List;

public class AccountHolderRelationsResponse extends AccountHolderResponse {

    public UserResponse user;
    public BankResponse bank;
    public List<AccountResponse> accounts;

}
