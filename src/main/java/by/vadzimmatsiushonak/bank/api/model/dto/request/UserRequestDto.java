package by.vadzimmatsiushonak.bank.api.model.dto.request;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class UserRequestDto {

    @ApiModelProperty(required = true)
    @NotNull
    public String login;

    @ApiModelProperty(notes = "'password' must be between 8 and 50 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and one special character.",
            example = "MyP@ssw0rd123",
            required = true)
    @Size(min = 8, max = 50)
    // TODO: Add password pattern
//    @Pattern(
//        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
//        message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character"
//    )
    @NotNull
    public String password;

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
