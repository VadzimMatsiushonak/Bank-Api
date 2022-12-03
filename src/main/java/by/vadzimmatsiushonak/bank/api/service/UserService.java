package by.vadzimmatsiushonak.bank.api.service;

import by.vadzimmatsiushonak.bank.api.model.entity.User;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotNull;

public interface UserService {

    User create(@NotNull User user);

    Optional<User> findById(@NotNull Long id);

    List<User> findAll();

    void update(@NotNull User user);

    void delete(@NotNull User user);

    void deleteById(@NotNull Long id);
}
