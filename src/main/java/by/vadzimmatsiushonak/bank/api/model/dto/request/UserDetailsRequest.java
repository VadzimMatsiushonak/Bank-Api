package by.vadzimmatsiushonak.bank.api.model.dto.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class UserDetailsRequest {

    @ApiModelProperty(notes = "'name' must start with capital letter",
            required = true)
    @NotNull
    public String name;

    @ApiModelProperty(notes = "'surname' must start with capital letter",
            required = true)
    @NotNull
    public String surname;

    @ApiModelProperty(required = true)
    @NotNull
    public LocalDate dateOfBirth;

    @ApiModelProperty(notes = "'phoneNumber' must be separated with spaces, countryCode operatorCode number",
            example = "1 23 1234567",
            required = true)
    @NotNull
    public String phoneNumber;
}
