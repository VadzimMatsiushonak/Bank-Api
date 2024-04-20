package by.vadzimmatsiushonak.bank.api.mapper;

import by.vadzimmatsiushonak.bank.api.model.dto.request.AccountRequest;
import by.vadzimmatsiushonak.bank.api.model.dto.response.AccountResponse;
import by.vadzimmatsiushonak.bank.api.model.dto.response.relations.AccountRelationsResponse;
import by.vadzimmatsiushonak.bank.api.model.entity.Account;
import by.vadzimmatsiushonak.bank.api.model.entity.AccountHolder;
import by.vadzimmatsiushonak.bank.api.model.entity.Bank;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Named(value = "plain")
    AccountResponse toResponse(Account entity);

    @IterableMapping(qualifiedByName = "plain")
    List<AccountResponse> toListResponse(List<Account> entities);

    @Named(value = "relations")
    AccountRelationsResponse toResponseRelations(Account entity);

    @IterableMapping(qualifiedByName = "relations")
    List<AccountRelationsResponse> toListResponseRelations(List<Account> entities);

    @Mapping(target = "accountHolder", source = "accountHolderId")
    @Mapping(target = "bank", source = "bankId")
    Account toEntity(AccountRequest request);

    default AccountHolder fromIdToAccountHolder(Long accountHolderId) {
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setId(accountHolderId);
        return accountHolder;
    }

    default Bank fromIdToBank(Long bankId) {
        Bank bank = new Bank();
        bank.setId(bankId);
        return bank;
    }

}
