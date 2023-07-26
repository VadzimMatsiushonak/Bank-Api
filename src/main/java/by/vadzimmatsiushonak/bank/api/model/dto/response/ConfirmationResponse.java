package by.vadzimmatsiushonak.bank.api.model.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ConfirmationResponse {

    @ApiModelProperty(value = "Indicates the bank payment Confirmation was successful.", example = "true")
    public Boolean isConfirmed;
}
