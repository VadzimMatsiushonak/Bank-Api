package by.vadzimmatsiushonak.bank.api.model.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthResponse {

    @ApiModelProperty(value = "The UUID confirmation key", example = "L_7c2a6f90-6d5b-11eb-8c03-0242ac130003")
    public String confirmationKey;

}
