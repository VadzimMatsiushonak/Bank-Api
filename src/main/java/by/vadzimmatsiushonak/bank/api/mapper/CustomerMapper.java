package by.vadzimmatsiushonak.bank.api.mapper;

import by.vadzimmatsiushonak.bank.api.model.dto.request.CustomerRequestDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.CustomerDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.relations.CustomerDtoRelations;
import by.vadzimmatsiushonak.bank.api.model.entity.Customer;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDto toDto(Customer entity);

    CustomerDtoRelations toDtoRelations(Customer entity);

    List<CustomerDtoRelations> toListDtoRelations(List<Customer> entities);

    Customer toEntity(CustomerRequestDto dto);

}
