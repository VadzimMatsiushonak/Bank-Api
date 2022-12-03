package by.vadzimmatsiushonak.bank.api.model.dto.request;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;


public class InitiatePaymentRequest {

    @NotNull
    public BigDecimal amount;

    @NotNull
    public String currency;

    @NotNull
    public Long recipientBankAccountId;

    @NotNull
    public Long senderBankAccountId;

}
