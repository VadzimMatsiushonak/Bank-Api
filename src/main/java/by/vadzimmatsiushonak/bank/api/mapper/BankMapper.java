package by.vadzimmatsiushonak.bank.api.mapper;

import by.vadzimmatsiushonak.bank.api.model.dto.request.BankRequest;
import by.vadzimmatsiushonak.bank.api.model.dto.response.BankResponse;
import by.vadzimmatsiushonak.bank.api.model.dto.response.relations.BankRelationsResponse;
import by.vadzimmatsiushonak.bank.api.model.entity.AccountHolder;
import by.vadzimmatsiushonak.bank.api.model.entity.Bank;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BankMapper {

    @Named(value = "plain")
    BankResponse toResponse(Bank entity);

    @IterableMapping(qualifiedByName = "plain")
    List<BankResponse> toListResponse(List<Bank> entities);

    @Named(value = "relations")
    BankRelationsResponse toResponseRelations(Bank entity);

    @IterableMapping(qualifiedByName = "relations")
    List<BankRelationsResponse> toListResponseRelations(List<Bank> entities);

    @Mapping(target = "accountHolder", source = "accountHolderId")
    Bank toEntity(BankRequest request);

    default AccountHolder fromIdToAccountHolder(Long accountHolderId) {
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setId(accountHolderId);
        return accountHolder;
    }

}
