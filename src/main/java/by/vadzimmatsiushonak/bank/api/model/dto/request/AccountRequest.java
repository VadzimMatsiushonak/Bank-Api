package by.vadzimmatsiushonak.bank.api.model.dto.request;

import by.vadzimmatsiushonak.bank.api.model.entity.base.AccountType;
import by.vadzimmatsiushonak.bank.api.model.entity.base.Currency;
import by.vadzimmatsiushonak.bank.api.model.entity.base.ModelStatus;
import by.vadzimmatsiushonak.bank.api.model.entity.base.TransactionType;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class AccountRequest {

    @ApiModelProperty(notes = "IBAN structure: 2 Letter Country Code + Mobile Operator Code + First Letter Of Name + First Letter Of Surname + Mobile Number", example = "BY44VM1234567", required = true)
    @NotNull
    public String iban;

    @ApiModelProperty(required = true)
    @NotNull
    public String name;

    public String description;

    @ApiModelProperty(example = "PERSONAL")
    @NotNull
    public AccountType type;

    @ApiModelProperty(example = "DEBIT")
    @NotNull
    public TransactionType transactionType;

    @ApiModelProperty(notes = "'amount' >= 0.0 value must be higher or equal to 0.0", example = "0.0", required = true)
    @DecimalMin(value = "0.0")
    @NotNull
    public BigDecimal amount;

    @ApiModelProperty(example = "USD", required = true)
    @NotNull
    public Currency currency;

    public ModelStatus status;

    @ApiModelProperty(example = "1", required = true)
    @NotNull
    public Long accountHolderId;

    @ApiModelProperty(example = "1", required = true)
    @NotNull
    public Long bankId;

}
