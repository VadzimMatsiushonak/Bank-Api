package by.vadzimmatsiushonak.bank.api.mapper;

import by.vadzimmatsiushonak.bank.api.model.dto.request.BankRequestDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.BankDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.relations.BankDtoRelations;
import by.vadzimmatsiushonak.bank.api.model.entity.Bank;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BankMapper {

    BankDto toDto(Bank entity);

    BankDtoRelations toDtoRelations(Bank entity);

    List<BankDtoRelations> toListDtoRelations(List<Bank> entities);

    Bank toEntity(BankRequestDto dto);
}
