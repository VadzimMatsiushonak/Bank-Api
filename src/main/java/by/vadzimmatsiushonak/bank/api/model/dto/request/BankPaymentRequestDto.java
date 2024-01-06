package by.vadzimmatsiushonak.bank.api.model.dto.request;

import by.vadzimmatsiushonak.bank.api.model.entity.base.Currency;
import by.vadzimmatsiushonak.bank.api.model.entity.base.TransactionStatus;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class BankPaymentRequestDto {

    @ApiModelProperty(notes = "'amount' >= 1.0 value must be higher or equal to 1.0", example = "1.0", required = true)
    @DecimalMin(value = "1.0")
    @NotNull
    public BigDecimal amount;

    @ApiModelProperty(example = "USD", required = true)
    @NotNull
    public Currency currency;

    @ApiModelProperty(notes = "'recipientBankAccountId' >= 1 value must be higher or equal to 1\n'recipientBankAccountId' != 'senderBankAccountId' ids must not be the same", example = "2", required = true)
    @NotNull
    public Long recipientBankAccountId;

    @ApiModelProperty(example = "ACCEPTED", required = true)
    @NotNull
    public TransactionStatus status;

    @ApiModelProperty(notes = "IBAN structure: 2 Letter Country Code + Mobile Operator Code + First Letter Of Name + First Letter Of Surname + Mobile Number", example = "BY29VM1234567", required = true)
    @NotNull
    public String bankAccountIban;

}
