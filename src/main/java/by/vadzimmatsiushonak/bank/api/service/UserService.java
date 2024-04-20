package by.vadzimmatsiushonak.bank.api.service;

import by.vadzimmatsiushonak.bank.api.model.entity.User;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotNull;

public interface UserService {

    User save(@NotNull User user);

    Optional<User> findById(@NotNull Long id);

    Optional<User> findByLogin(@NotNull String login);

    Optional<User> findByPhoneNumber(String phoneNumber);

    List<User> findAll();

    User update(@NotNull User user);

    void delete(@NotNull User user);

    void deleteById(@NotNull Long id);
}
