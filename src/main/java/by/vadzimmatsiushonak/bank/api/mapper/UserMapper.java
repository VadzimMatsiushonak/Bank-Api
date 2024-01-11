package by.vadzimmatsiushonak.bank.api.mapper;

import by.vadzimmatsiushonak.bank.api.model.dto.request.UserRequest;
import by.vadzimmatsiushonak.bank.api.model.dto.response.UserResponse;
import by.vadzimmatsiushonak.bank.api.model.dto.response.relations.UserRelationsResponse;
import by.vadzimmatsiushonak.bank.api.model.entity.User;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Named(value = "plain")
    UserResponse toResponse(User entity);

    @IterableMapping(qualifiedByName = "plain")
    List<UserResponse> toListResponse(List<User> entities);

    @Named(value = "relations")
    UserRelationsResponse toResponseRelations(User entity);

    @IterableMapping(qualifiedByName = "relations")
    List<UserRelationsResponse> toListResponseRelations(List<User> entities);

    User toEntity(UserRequest request);

}
