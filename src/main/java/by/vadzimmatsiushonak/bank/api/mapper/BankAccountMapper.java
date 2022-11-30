package by.vadzimmatsiushonak.bank.api.mapper;

import by.vadzimmatsiushonak.bank.api.model.dto.request.BankAccountRequestDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.BankAccountDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.relations.BankAccountDtoRelations;
import by.vadzimmatsiushonak.bank.api.model.entity.Bank;
import by.vadzimmatsiushonak.bank.api.model.entity.BankAccount;
import by.vadzimmatsiushonak.bank.api.model.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {

    BankAccountDto toDto(BankAccount entity);

    BankAccountDtoRelations toDtoRelations(BankAccount entity);

    List<BankAccountDtoRelations> toListDtoRelations(List<BankAccount> entities);

    @Mapping(target = "bank", source = "bankId")
    @Mapping(target = "customer", source = "customerId")
    BankAccount toEntity(BankAccountRequestDto dto);

    default Bank fromIdToBank(Long bankId) {
        Bank bank = new Bank();
        bank.setId(bankId);
        return bank;
    }

    default Customer fromIdToCustomer(Long customerId) {
        Customer customer = new Customer();
        customer.setId(customerId);
        return customer;
    }

}
