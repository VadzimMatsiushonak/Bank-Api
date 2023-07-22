package by.vadzimmatsiushonak.bank.api.mapper;

import by.vadzimmatsiushonak.bank.api.model.dto.request.UserRequestDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.UserDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.relations.UserDtoRelations;
import by.vadzimmatsiushonak.bank.api.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    UserDto toDto(User entity);

    UserDtoRelations toDtoRelations(User entity);

    List<UserDtoRelations> toListDtoRelations(List<User> entities);

    User toEntity(UserRequestDto dto);

}
