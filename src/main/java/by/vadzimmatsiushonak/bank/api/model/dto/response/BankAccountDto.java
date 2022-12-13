package by.vadzimmatsiushonak.bank.api.model.dto.response;

import by.vadzimmatsiushonak.bank.api.model.dto.base.BaseEntityDto;
import by.vadzimmatsiushonak.bank.api.model.entity.base.Currency;
import by.vadzimmatsiushonak.bank.api.model.entity.base.OperationType;
import java.math.BigDecimal;

public class BankAccountDto extends BaseEntityDto {

    public String title;

    public String iban;

    public Currency currency;

    public BigDecimal amount;

    public OperationType type;

}
