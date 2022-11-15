package by.vadzimmatsiushonak.bank.api.model.dto.relations;

import by.vadzimmatsiushonak.bank.api.model.dto.BankAccountDto;
import by.vadzimmatsiushonak.bank.api.model.dto.CustomerDto;

import java.util.List;

public class CustomerDtoRelations extends CustomerDto {

    public List<BankAccountDto> bankAccounts;

}
