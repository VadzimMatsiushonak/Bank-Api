package by.vadzimmatsiushonak.bank.api.model.dto.response.relations;

import by.vadzimmatsiushonak.bank.api.model.dto.response.BankAccountDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.BankPaymentDto;

public class BankPaymentDtoRelations extends BankPaymentDto {

    public BankAccountDto bankAccount;

}
