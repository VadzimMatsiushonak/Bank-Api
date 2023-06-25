package by.vadzimmatsiushonak.bank.api.model.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class VerificationResponse {

    @ApiModelProperty(value = "Indicates the user account Verification was successful.", example = "true")
    public Boolean isVerified;

}
