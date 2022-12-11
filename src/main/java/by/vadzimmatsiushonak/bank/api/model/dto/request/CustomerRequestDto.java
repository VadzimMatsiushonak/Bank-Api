package by.vadzimmatsiushonak.bank.api.model.dto.request;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CustomerRequestDto {

    @ApiModelProperty(notes = "'name' must start with capital letter", required = true)
    @NotNull
    public String name;

    @ApiModelProperty(notes = "'name' must start with capital letter", required = true)
    @NotNull
    public String surname;

    @ApiModelProperty(required = true)
    @NotNull
    public LocalDate dateOfBirth;

    @ApiModelProperty(notes = "'phoneNumber' must be separated with spaces, countryCode operatorCode number", example = "1 23 1234567", required = true)
    @NotNull
    public String phoneNumber;

    @ApiModelProperty(required = true)
    @Size(min = 8)
    @NotNull
    public String password;

}
