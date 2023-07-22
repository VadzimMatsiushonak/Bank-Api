package by.vadzimmatsiushonak.bank.api.mapper;

import by.vadzimmatsiushonak.bank.api.model.dto.request.BankAccountRequestDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.BankAccountDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.relations.BankAccountDtoRelations;
import by.vadzimmatsiushonak.bank.api.model.entity.Bank;
import by.vadzimmatsiushonak.bank.api.model.entity.BankAccount;
import by.vadzimmatsiushonak.bank.api.model.entity.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {

    BankAccountDto toDto(BankAccount entity);

    BankAccountDtoRelations toDtoRelations(BankAccount entity);

    List<BankAccountDtoRelations> toListDtoRelations(List<BankAccount> entities);

    @Mapping(target = "bank", source = "bankId")
    @Mapping(target = "user", source = "userId")
    BankAccount toEntity(BankAccountRequestDto dto);

    default Bank fromIdToBank(Long bankId) {
        Bank bank = new Bank();
        bank.setId(bankId);
        return bank;
    }

    default User fromIdToUser(Long userId) {
        User user = new User();
        user.setId(userId);
        return user;
    }

}
