package by.vadzimmatsiushonak.bank.api.service.impl;

import by.vadzimmatsiushonak.bank.api.model.entity.Customer;
import by.vadzimmatsiushonak.bank.api.repository.CustomerRepository;
import by.vadzimmatsiushonak.bank.api.service.CustomerService;
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
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    @Override
    public void create(@NotNull Customer customer) {
        log.info("CustomerServiceImpl create {}", customer);

        repository.save(customer);
    }

    @Override
    public Optional<Customer> findById(@NotNull Long id) {
        log.info("CustomerServiceImpl findById {}", id);

        return repository.findById(id);
    }

    @Override
    public List<Customer> findAll() {
        log.info("CustomerServiceImpl findAll");

        return repository.findAll();
    }

    @Override
    public void update(@NotNull Customer customer) {
        log.info("CustomerServiceImpl update {}", customer);

        Objects.requireNonNull(customer.getId());
        repository.save(customer);
    }

    @Override
    public void delete(@NotNull Customer customer) {
        log.info("CustomerServiceImpl delete {}", customer);

        repository.delete(customer);
    }

    @Override
    public void deleteById(@NotNull Long id) {
        log.info("CustomerServiceImpl deleteById {}", id);

        repository.deleteById(id);
    }
}
