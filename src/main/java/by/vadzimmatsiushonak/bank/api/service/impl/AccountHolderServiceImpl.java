package by.vadzimmatsiushonak.bank.api.service.impl;

import by.vadzimmatsiushonak.bank.api.model.entity.AccountHolder;
import by.vadzimmatsiushonak.bank.api.repository.AccountHolderRepository;
import by.vadzimmatsiushonak.bank.api.service.AccountHolderService;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@RequiredArgsConstructor
@Validated
@Slf4j
@Service
public class AccountHolderServiceImpl implements AccountHolderService {

    private final AccountHolderRepository repository;

    @Override
    public AccountHolder save(@NotNull AccountHolder accountHolder) {
        log.info("AccountHolderServiceImpl create {}", accountHolder);
        accountHolder.setId(null);

        return repository.save(accountHolder);
    }

    @Override
    public Optional<AccountHolder> findById(@NotNull Long id) {
        log.info("AccountHolderServiceImpl findById {}", id);

        return repository.findById(id);
    }

    @Override
    public Optional<AccountHolder> findByUserId(Long id) {
        log.info("AccountHolderServiceImpl findByUserId {}", id);

        return repository.findByUserId(id);
    }

    @Override
    public Optional<AccountHolder> findByUserLogin(@NotNull String login) {
        log.info("AccountHolderServiceImpl findByUserLogin {}", login);

        return repository.findByUserLogin(login);
    }

    @Override
    public List<AccountHolder> findAll() {
        log.info("AccountHolderServiceImpl findAll");

        return repository.findAll();
    }

    @Override
    public AccountHolder update(@NotNull AccountHolder accountHolder) {
        log.info("AccountHolderServiceImpl update {}", accountHolder);

        Objects.requireNonNull(accountHolder.getId());
        return repository.save(accountHolder);
    }

    @Override
    public void delete(@NotNull AccountHolder accountHolder) {
        log.info("AccountHolderServiceImpl delete {}", accountHolder);

        repository.delete(accountHolder);
    }

    @Override
    public void deleteById(@NotNull Long id) {
        log.info("AccountHolderServiceImpl deleteById {}", id);

        repository.deleteById(id);
    }
}
