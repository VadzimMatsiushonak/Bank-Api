package by.vadzimmatsiushonak.bank.api.model.dto.response.relations;

import by.vadzimmatsiushonak.bank.api.model.dto.response.BankAccountDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.BankCardDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.BankDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.BankPaymentDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.UserDto;

import java.util.List;

public class BankAccountDtoRelations extends BankAccountDto {

    public BankDto bank;
    public BankCardDto bankCard;
    public List<BankPaymentDto> bankPayments;
    public UserDto user;

}
