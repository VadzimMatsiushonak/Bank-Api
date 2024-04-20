package by.vadzimmatsiushonak.bank.api.model.dto.request;

import by.vadzimmatsiushonak.bank.api.model.entity.base.Currency;
import by.vadzimmatsiushonak.bank.api.model.entity.base.TransactionCategory;
import by.vadzimmatsiushonak.bank.api.model.entity.base.TransactionStatus;
import by.vadzimmatsiushonak.bank.api.model.entity.base.TransactionType;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TransactionRequest {

    @ApiModelProperty(notes = "'amount' >= 1.0 value must be higher or equal to 1.0", example = "1.0", required = true)
    @DecimalMin(value = "1.0")
    @NotNull
    public BigDecimal amount;

    public BigDecimal actualAmount;

    @ApiModelProperty(example = "USD", required = true)
    @NotNull
    public Currency currency;

    public BigDecimal feePercent;

    public BigDecimal feeAmount;

    public String details;

    public LocalDateTime processDate;

    @ApiModelProperty(example = "USD", required = true)
    @NotNull
    public TransactionCategory category;

    @ApiModelProperty(example = "USD", required = true)
    @NotNull
    public TransactionType type;

    public TransactionStatus status;

    @ApiModelProperty(notes = "IBAN structure: 2 Letter Country Code + Mobile Operator Code + First Letter Of Name + First Letter Of Surname + Mobile Number", example = "BY29VM1234567", required = true)
    @NotNull
    public String senderAccountIban;

    @ApiModelProperty(notes = "'cardNumber'.length == 16 value must be 16 characters long", example = "1234567890123456")
    @Size(min = 16, max = 16)
    @NotNull
    public String senderCardNumber;

    @ApiModelProperty(notes = "IBAN structure: 2 Letter Country Code + Mobile Operator Code + First Letter Of Name + First Letter Of Surname + Mobile Number", example = "BY29VM1234567", required = true)
    @NotNull
    public String recipientAccountIban;

    @ApiModelProperty(notes = "'cardNumber'.length == 16 value must be 16 characters long", example = "1234567890123456")
    @Size(min = 16, max = 16)
    @NotNull
    public String recipientCardNumber;

}
