package by.vadzimmatsiushonak.bank.api.model.dto.response.relations;

import by.vadzimmatsiushonak.bank.api.model.dto.response.BankAccountDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.CustomerDto;

import java.util.List;

public class CustomerDtoRelations extends CustomerDto {

    public List<BankAccountDto> bankAccounts;

}
