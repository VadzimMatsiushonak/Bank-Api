package by.vadzimmatsiushonak.bank.api.model.dto.response;

import by.vadzimmatsiushonak.bank.api.model.dto.base.BaseEntityDto;
import java.math.BigDecimal;

public class BankPaymentDto extends BaseEntityDto {

    public BigDecimal amount;
    public String currency;
    public Long recipientBankAccountId;

}
