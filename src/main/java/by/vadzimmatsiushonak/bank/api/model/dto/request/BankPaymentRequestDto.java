package by.vadzimmatsiushonak.bank.api.model.dto.request;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class BankPaymentRequestDto {

    public BigDecimal amount;
    public String currency;
    public Long recipientBankAccountId;

    @ApiModelProperty(example = "1")
    public Long bankAccountId;

}
