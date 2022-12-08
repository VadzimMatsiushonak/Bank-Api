package by.vadzimmatsiushonak.bank.api.model.dto.request;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;

public class InitiatePaymentRequest {

    @ApiModelProperty(notes = "<h4><i>Requirements:</i></h4>'amount' >= 1.0 value should be higher or equal to 1.0", example = "1.0", required = true)
    @NotNull
    public BigDecimal amount;

    @ApiModelProperty(notes = "<h4><i>Available currencies:</i></h4>[USD, EUR, BYN]", example = "USD", required = true)
    @NotNull
    public String currency;

    @ApiModelProperty(notes = "<h4><i>Requirements:</i></h4>'senderBankAccountId' >= 1 value should be higher or equal to 1\n'senderBankAccountId' != 'recipientBankAccountId' ids must not be the same", example = "1", required = true)
    @NotNull
    public Long senderBankAccountId;

    @ApiModelProperty(notes = "<h4><i>Requirements:</i></h4>'recipientBankAccountId' >= 1 value should be higher or equal to 1\n'recipientBankAccountId' != 'senderBankAccountId' ids must not be the same", example = "2", required = true)
    @NotNull
    public Long recipientBankAccountId;

}
