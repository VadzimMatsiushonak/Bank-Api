package by.vadzimmatsiushonak.bank.api.model.dto.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class AuthRequest {

    @ApiModelProperty(required = true, position = 10)
    @NotNull
    public String username;

    @ApiModelProperty(required = true, position = 1)
    @NotNull
    public String password;

}
