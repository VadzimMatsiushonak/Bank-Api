package by.vadzimmatsiushonak.bank.api.mapper;

import by.vadzimmatsiushonak.bank.api.model.dto.request.BankCardRequestDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.BankCardDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.relations.BankCardDtoRelations;
import by.vadzimmatsiushonak.bank.api.model.entity.BankAccount;
import by.vadzimmatsiushonak.bank.api.model.entity.BankCard;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BankCardMapper {

    BankCardDto toDto(BankCard entity);

    BankCardDtoRelations toDtoRelations(BankCard entity);

    List<BankCardDtoRelations> toListDtoRelations(List<BankCard> entities);

    @Mapping(target = "bankAccount", source = "bankAccountId")
    BankCard toEntity(BankCardRequestDto dto);

    default BankAccount fromIdToBankAccount(Long bankAccountId) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(bankAccountId);
        return bankAccount;
    }
}
