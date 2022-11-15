package by.vadzimmatsiushonak.bank.api.mapper;

import by.vadzimmatsiushonak.bank.api.model.dto.BankPaymentDto;
import by.vadzimmatsiushonak.bank.api.model.dto.relations.BankPaymentDtoRelations;
import by.vadzimmatsiushonak.bank.api.model.entity.BankPayment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BankPaymentMapper {

    BankPaymentDto toDto(BankPayment entity);

    BankPaymentDtoRelations toDtoRelations(BankPayment entity);

    List<BankPaymentDtoRelations> toListDtoRelations(List<BankPayment> entities);
}
