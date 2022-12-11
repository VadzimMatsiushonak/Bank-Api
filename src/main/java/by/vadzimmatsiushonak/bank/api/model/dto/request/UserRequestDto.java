package by.vadzimmatsiushonak.bank.api.model.dto.request;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;

public class UserRequestDto {


    @ApiModelProperty(required = true)
    @NotNull
    private String login;

    @ApiModelProperty(required = true)
    @NotNull
    private String password;

    @ApiModelProperty(required = true)
    @NotNull
    private String role;

}
