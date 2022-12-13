package by.vadzimmatsiushonak.bank.api.model.dto.response;

import by.vadzimmatsiushonak.bank.api.model.dto.base.BaseEntityDto;
import by.vadzimmatsiushonak.bank.api.model.entity.base.Currency;
import java.math.BigDecimal;

public class BankPaymentDto extends BaseEntityDto {

    public BigDecimal amount;
    public Currency currency;
    public Long recipientBankAccountId;
    public String status;

}
