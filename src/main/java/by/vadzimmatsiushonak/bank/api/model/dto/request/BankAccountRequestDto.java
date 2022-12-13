package by.vadzimmatsiushonak.bank.api.model.dto.request;

import by.vadzimmatsiushonak.bank.api.model.entity.base.Currency;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class BankAccountRequestDto {


    @ApiModelProperty(required = true)
    @NotNull
    public String title;

    @ApiModelProperty(notes = "IBAN structure: 2 Letter Country Code + Mobile Operator Code + First Letter Of Name + First Letter Of Surname + Mobile Number", example = "BY29VM123456", required = true)
    @NotNull
    public String iban;

    @ApiModelProperty(example = "USD", required = true)
    @NotNull
    public Currency currency;

    @ApiModelProperty(notes = "'amount' >= 0.0 value must be higher or equal to 0.0", example = "0.0", required = true)
    @DecimalMin(value = "0.0")
    @NotNull
    public BigDecimal amount;

    @ApiModelProperty(example = "debit")
    @NotNull
    public String type;

    @ApiModelProperty(example = "1", required = true)
    @NotNull
    public Long bankId;

    @ApiModelProperty(example = "1", required = true)
    @NotNull
    public Long customerId;

}
