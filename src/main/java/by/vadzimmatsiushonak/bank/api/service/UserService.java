package by.vadzimmatsiushonak.bank.api.service;

import by.vadzimmatsiushonak.bank.api.model.entity.User;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User create(@NotNull User user);

    Optional<User> findById(@NotNull Long id);

    List<User> findAll();

    void update(@NotNull User user);

    void delete(@NotNull User user);

    void deleteById(@NotNull Long id);
}
