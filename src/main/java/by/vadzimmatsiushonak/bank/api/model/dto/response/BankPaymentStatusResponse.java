package by.vadzimmatsiushonak.bank.api.model.dto.response;

import by.vadzimmatsiushonak.bank.api.model.dto.base.BaseEntityDto;
import by.vadzimmatsiushonak.bank.api.model.entity.base.PaymentStatus;

public class BankPaymentStatusResponse extends BaseEntityDto {

    public PaymentStatus status;
}
