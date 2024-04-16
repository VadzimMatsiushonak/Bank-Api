package by.vadzimmatsiushonak.bank.api.model.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class ConfirmationResponse {

    @ApiModelProperty(value = "Indicates the user's action is confirmed successful.", example = "true")
    public Boolean isConfirmed;

    public Object metadata;

    public ConfirmationResponse(Boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

}
