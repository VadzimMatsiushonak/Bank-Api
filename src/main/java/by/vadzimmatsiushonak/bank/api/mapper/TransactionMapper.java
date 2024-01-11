package by.vadzimmatsiushonak.bank.api.mapper;

import by.vadzimmatsiushonak.bank.api.model.dto.request.TransactionRequest;
import by.vadzimmatsiushonak.bank.api.model.dto.response.TransactionResponse;
import by.vadzimmatsiushonak.bank.api.model.dto.response.relations.TransactionRelationsResponse;
import by.vadzimmatsiushonak.bank.api.model.entity.Account;
import by.vadzimmatsiushonak.bank.api.model.entity.Card;
import by.vadzimmatsiushonak.bank.api.model.entity.Transaction;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Named(value = "plain")
    TransactionResponse toResponse(Transaction entity);

    @IterableMapping(qualifiedByName = "plain")
    List<TransactionResponse> toListResponse(List<Transaction> entities);

    @Named(value = "relations")
    TransactionRelationsResponse toResponseRelations(Transaction entity);

    @IterableMapping(qualifiedByName = "relations")
    List<TransactionRelationsResponse> toListResponseRelations(List<Transaction> entities);

    @Mapping(target = "sender", source = "senderAccountIban")
    @Mapping(target = "senderCard", source = "senderCardNumber")
    @Mapping(target = "recipient", source = "recipientAccountIban")
    @Mapping(target = "recipientCard", source = "recipientCardNumber")
    Transaction toEntity(TransactionRequest request);

    default Account fromIdToAccount(String accountIban) {
        Account account = new Account();
        account.setIban(accountIban);
        return account;
    }

    default Card fromIdToCard(String cardNumber) {
        Card card = new Card();
        card.setCardNumber(cardNumber);
        return card;
    }

}
