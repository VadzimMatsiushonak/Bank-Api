package by.vadzimmatsiushonak.bank.api.mapper;

import by.vadzimmatsiushonak.bank.api.model.dto.request.CardRequest;
import by.vadzimmatsiushonak.bank.api.model.dto.response.CardResponse;
import by.vadzimmatsiushonak.bank.api.model.dto.response.relations.CardRelationsResponse;
import by.vadzimmatsiushonak.bank.api.model.entity.Account;
import by.vadzimmatsiushonak.bank.api.model.entity.Bank;
import by.vadzimmatsiushonak.bank.api.model.entity.Card;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CardMapper {

    @Named(value = "plain")
    CardResponse toResponse(Card entity);

    @IterableMapping(qualifiedByName = "plain")
    List<CardResponse> toListResponse(List<Card> entities);

    @Named(value = "relations")
    CardRelationsResponse toResponseRelations(Card entity);

    @IterableMapping(qualifiedByName = "relations")
    List<CardRelationsResponse> toListResponseRelations(List<Card> entities);

    @Mapping(target = "account", source = "accountIban")
    @Mapping(target = "bank", source = "bankId")
    Card toEntity(CardRequest request);

    default Account fromIdToAccount(String accountIban) {
        Account account = new Account();
        account.setIban(accountIban);
        return account;
    }

    default Bank fromIdToBank(Long bankId) {
        Bank bank = new Bank();
        bank.setId(bankId);
        return bank;
    }

}
