package by.vadzimmatsiushonak.bank.api.model.dto.response.relations;

import by.vadzimmatsiushonak.bank.api.model.dto.response.BankAccountDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.UserDto;

import java.util.List;

public class UserDtoRelations extends UserDto {

    public List<BankAccountDto> bankAccounts;

}
