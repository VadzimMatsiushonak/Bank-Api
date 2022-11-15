package by.vadzimmatsiushonak.bank.api.mapper;

import by.vadzimmatsiushonak.bank.api.model.dto.CustomerDto;
import by.vadzimmatsiushonak.bank.api.model.dto.relations.CustomerDtoRelations;
import by.vadzimmatsiushonak.bank.api.model.entity.Customer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDto toDto(Customer entity);

    CustomerDtoRelations toDtoRelations(Customer entity);

    List<CustomerDtoRelations> toListDtoRelations(List<Customer> entities);

}
