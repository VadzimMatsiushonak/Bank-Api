package by.vadzimmatsiushonak.bank.api.mapper;

import by.vadzimmatsiushonak.bank.api.model.dto.request.AccountHolderRequest;
import by.vadzimmatsiushonak.bank.api.model.dto.response.AccountHolderResponse;
import by.vadzimmatsiushonak.bank.api.model.dto.response.relations.AccountHolderRelationsResponse;
import by.vadzimmatsiushonak.bank.api.model.entity.Account;
import by.vadzimmatsiushonak.bank.api.model.entity.AccountHolder;
import by.vadzimmatsiushonak.bank.api.model.entity.Bank;
import by.vadzimmatsiushonak.bank.api.model.entity.User;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AccountHolderMapper {

    @Named(value = "plain")
    AccountHolderResponse toResponse(AccountHolder entity);

    @IterableMapping(qualifiedByName = "plain")
    List<AccountHolderResponse> toListResponse(List<AccountHolder> entities);

    @Named(value = "relations")
    AccountHolderRelationsResponse toResponseRelations(AccountHolder entity);

    @IterableMapping(qualifiedByName = "relations")
    List<AccountHolderRelationsResponse> toListResponseRelations(List<AccountHolder> entities);

    @Mapping(target = "user", source = "userId")
    @Mapping(target = "bank", source = "bankId")
    @Mapping(target = "accounts", source = "accountIbans")
    AccountHolder toEntity(AccountHolderRequest request);

    default User fromIdToUser(Long userId) {
        User user = new User();
        user.setId(userId);
        return user;
    }

    default Bank fromIdToBank(Long bankId) {
        Bank bank = new Bank();
        bank.setId(bankId);
        return bank;
    }

    default Account fromIdToAccount(String accountIban) {
        Account account = new Account();
        account.setIban(accountIban);
        return account;
    }

}
