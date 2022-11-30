package by.vadzimmatsiushonak.bank.api.mapper;

import by.vadzimmatsiushonak.bank.api.model.dto.request.BankPaymentRequestDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.BankPaymentDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.relations.BankPaymentDtoRelations;
import by.vadzimmatsiushonak.bank.api.model.entity.BankAccount;
import by.vadzimmatsiushonak.bank.api.model.entity.BankPayment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BankPaymentMapper {

    BankPaymentDto toDto(BankPayment entity);

    BankPaymentDtoRelations toDtoRelations(BankPayment entity);

    List<BankPaymentDtoRelations> toListDtoRelations(List<BankPayment> entities);

    @Mapping(target = "bankAccount", source = "bankAccountId")
    BankPayment toEntity(BankPaymentRequestDto dto);

    default BankAccount fromIdToBankAccount(Long bankAccountId) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(bankAccountId);
        return bankAccount;
    }
}
