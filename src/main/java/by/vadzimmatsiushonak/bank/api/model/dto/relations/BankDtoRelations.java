package by.vadzimmatsiushonak.bank.api.model.dto.relations;

import by.vadzimmatsiushonak.bank.api.model.dto.BankAccountDto;
import by.vadzimmatsiushonak.bank.api.model.dto.BankDto;

import java.util.List;

public class BankDtoRelations extends BankDto {

    public List<BankAccountDto> bankAccounts;

}
