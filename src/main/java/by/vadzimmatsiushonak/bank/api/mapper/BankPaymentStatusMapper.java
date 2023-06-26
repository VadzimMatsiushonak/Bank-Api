package by.vadzimmatsiushonak.bank.api.mapper;

import by.vadzimmatsiushonak.bank.api.model.dto.response.BankPaymentStatusResponse;
import by.vadzimmatsiushonak.bank.api.model.entity.BankPayment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankPaymentStatusMapper {

    BankPaymentStatusResponse toDto(BankPayment entity);

}
