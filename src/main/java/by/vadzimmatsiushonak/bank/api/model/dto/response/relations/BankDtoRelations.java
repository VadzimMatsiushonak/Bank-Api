package by.vadzimmatsiushonak.bank.api.model.dto.response.relations;

import by.vadzimmatsiushonak.bank.api.model.dto.response.BankAccountDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.BankDto;
import java.util.List;

public class BankDtoRelations extends BankDto {

    public List<BankAccountDto> bankAccounts;

}
