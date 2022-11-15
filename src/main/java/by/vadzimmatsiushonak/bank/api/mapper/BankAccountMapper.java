package by.vadzimmatsiushonak.bank.api.mapper;

import by.vadzimmatsiushonak.bank.api.model.dto.BankAccountDto;
import by.vadzimmatsiushonak.bank.api.model.dto.relations.BankAccountDtoRelations;
import by.vadzimmatsiushonak.bank.api.model.entity.BankAccount;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {

    BankAccountDto toDto(BankAccount entity);

    BankAccountDtoRelations toDtoRelations(BankAccount entity);

    List<BankAccountDtoRelations> toListDtoRelations(List<BankAccount> entities);

}
