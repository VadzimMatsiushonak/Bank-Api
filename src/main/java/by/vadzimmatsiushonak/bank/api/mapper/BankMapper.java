package by.vadzimmatsiushonak.bank.api.mapper;

import by.vadzimmatsiushonak.bank.api.model.dto.request.BankRequestDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.BankDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.relations.BankDtoRelations;
import by.vadzimmatsiushonak.bank.api.model.entity.Bank;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BankMapper {

    BankDto toDto(Bank entity);

    BankDtoRelations toDtoRelations(Bank entity);

    List<BankDtoRelations> toListDtoRelations(List<Bank> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bankAccounts", ignore = true)
    Bank toEntity(BankRequestDto dto);
}
