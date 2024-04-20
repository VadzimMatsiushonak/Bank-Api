package by.vadzimmatsiushonak.bank.api.model.dto.request;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;

public class TokenRequest {

    @ApiModelProperty(required = true, position = 10)
    @NotNull
    public String key;

    @ApiModelProperty(required = true, position = 1)
    @NotNull
    public Integer code;

}
