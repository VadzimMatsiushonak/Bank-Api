package by.vadzimmatsiushonak.bank.api.mapper;

import by.vadzimmatsiushonak.bank.api.model.dto.BankCardDto;
import by.vadzimmatsiushonak.bank.api.model.dto.relations.BankCardDtoRelations;
import by.vadzimmatsiushonak.bank.api.model.entity.BankCard;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BankCardMapper {

    BankCardDto toDto(BankCard entity);

    BankCardDtoRelations toDtoRelations(BankCard entity);

    List<BankCardDtoRelations> toListDtoRelations(List<BankCard> entities);
}
