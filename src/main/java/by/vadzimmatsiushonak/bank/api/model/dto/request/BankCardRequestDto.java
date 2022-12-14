package by.vadzimmatsiushonak.bank.api.model.dto.request;

import by.vadzimmatsiushonak.bank.api.annotation.ValidExpirationDate;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BankCardRequestDto {

    @ApiModelProperty(notes = "'number'.length == 16 value must be 16 characters long", example = "1234567890123456", required = true)
    @Size(min = 16, max = 16)
    @NotNull
    public String number;

    @ApiModelProperty(notes = "'csv'.length == 3 value must be 3 characters long", example = "123", required = true)
    @Size(min = 3, max = 3)
    @NotNull
    public String cvs;

    @ApiModelProperty(notes = "'expirationDate' value must be one year greater than the current date", example = "2030-01-01", required = true)
    @ValidExpirationDate
    @NotNull
    public LocalDate expirationDate;

    @ApiModelProperty(example = "1", required = true)
    @NotNull
    public Long bankAccountId;

}
