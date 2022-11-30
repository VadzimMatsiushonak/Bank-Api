package by.vadzimmatsiushonak.bank.api.model.dto.response.relations;

import by.vadzimmatsiushonak.bank.api.model.dto.response.*;

import java.util.List;

public class BankAccountDtoRelations extends BankAccountDto {

    public BankDto bank;
    public BankCardDto bankCard;
    public List<BankPaymentDto> bankPayments;
    public CustomerDto customer;

}
