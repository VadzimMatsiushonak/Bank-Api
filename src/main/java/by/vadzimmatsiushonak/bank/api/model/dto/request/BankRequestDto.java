package by.vadzimmatsiushonak.bank.api.model.dto.request;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class BankRequestDto {

    @ApiModelProperty(required = true)
    @NotNull
    public String title;

    @ApiModelProperty(notes = "'amount' >= 1.0 value must be higher or equal to 1.0", example = "1.0", required = true)
    @DecimalMin(value = "1.0")
    @NotNull
    public BigDecimal amount;

}
