package by.vadzimmatsiushonak.bank.api.model.dto.response.relations;

import by.vadzimmatsiushonak.bank.api.model.dto.response.BankAccountDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.BankCardDto;

public class BankCardDtoRelations extends BankCardDto {

    public BankAccountDto bankAccount;

}
