package by.vadzimmatsiushonak.bank.api.service.impl;

import by.vadzimmatsiushonak.bank.api.model.entity.User;
import by.vadzimmatsiushonak.bank.api.repository.UserRepository;
import by.vadzimmatsiushonak.bank.api.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Validated
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public User create(@NotNull User user) {
        log.info("UserServiceImpl create {}", user);
        user.setId(null);

        return repository.save(user);
    }

    @Override
    public Optional<User> findById(@NotNull Long id) {
        log.info("UserServiceImpl findById {}", id);

        return repository.findById(id);
    }

    @Override
    public List<User> findAll() {
        log.info("UserServiceImpl findAll");

        return repository.findAll();
    }

    @Override
    public void update(@NotNull User user) {
        log.info("UserServiceImpl update {}", user);

        Objects.requireNonNull(user.getId());
        repository.save(user);
    }

    @Override
    public void delete(@NotNull User user) {
        log.info("UserServiceImpl delete {}", user);

        repository.delete(user);
    }

    @Override
    public void deleteById(@NotNull Long id) {
        log.info("UserServiceImpl deleteById {}", id);

        repository.deleteById(id);
    }
}
