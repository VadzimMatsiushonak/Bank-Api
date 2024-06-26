package by.vadzimmatsiushonak.bank.api.model.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InitiatedTransactionResponse {

    @ApiModelProperty(value = "The UUID confirmationKey key", example = "P_7c2a6f90-6d5b-11eb-8c03-0242ac130003")
    public String confirmationKey;

    @ApiModelProperty(value = "The transaction id", example = "1")
    public Long transactionId;
}
