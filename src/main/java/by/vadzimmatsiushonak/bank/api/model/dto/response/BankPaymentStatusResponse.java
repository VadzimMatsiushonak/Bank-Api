package by.vadzimmatsiushonak.bank.api.model.dto.response;

import by.vadzimmatsiushonak.bank.api.model.dto.base.BaseEntityDto;
import by.vadzimmatsiushonak.bank.api.model.entity.base.PaymentStatus;
import io.swagger.annotations.ApiModelProperty;

public class BankPaymentStatusResponse extends BaseEntityDto {

    @ApiModelProperty(value = "Contains information about the payment status.\n" +
            "Possible values are ACCEPTED, PENDING, and REJECTED.\n" +
            "ACCEPTED indicates that the payment was successful.\n" +
            "PENDING indicates that the payment is awaiting confirmation.\n" +
            "REJECTED indicates that there were issues with the payment.", example = "PENDING")
    public PaymentStatus status;
}
