package by.vadzimmatsiushonak.bank.api.model.dto;

import by.vadzimmatsiushonak.bank.api.model.dto.base.BaseEntityDto;

import java.math.BigDecimal;

public class BankAccountDto extends BaseEntityDto {

    public String title;
    public String iban;
    public String currency;
    public BigDecimal amount;
    public String type;

}
