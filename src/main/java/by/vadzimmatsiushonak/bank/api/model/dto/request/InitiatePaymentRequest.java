package by.vadzimmatsiushonak.bank.api.model.dto.request;

import by.vadzimmatsiushonak.bank.api.model.entity.base.Currency;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class InitiatePaymentRequest {

    @ApiModelProperty(notes = "'amount' >= 1.0 value must be higher or equal to 1.0", example = "1.0", required = true)
    @DecimalMin(value = "1.0")
    @NotNull
    public BigDecimal amount;

    @ApiModelProperty(example = "USD", required = true)
    @NotNull
    public Currency currency;

    @ApiModelProperty(notes = "'senderBankAccountId' >= 1 value must be higher or equal to 1\n'senderBankAccountId' != 'recipientBankAccountId' ids must not be the same", example = "1", required = true)
    @NotNull
    public Long senderBankAccountId;

    @ApiModelProperty(notes = "'recipientBankAccountId' >= 1 value must be higher or equal to 1\n'recipientBankAccountId' != 'senderBankAccountId' ids must not be the same", example = "2", required = true)
    @NotNull
    public Long recipientBankAccountId;

}
