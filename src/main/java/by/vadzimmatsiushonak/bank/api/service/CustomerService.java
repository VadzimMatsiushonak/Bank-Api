package by.vadzimmatsiushonak.bank.api.service;

import by.vadzimmatsiushonak.bank.api.model.entity.Customer;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotNull;

public interface CustomerService {

    Customer save(@NotNull Customer customer);

    Optional<Customer> findById(@NotNull Long id);

    Optional<Customer> findByPhoneNumber(String phoneNumber);

    List<Customer> findAll();

    void update(@NotNull Customer customer);

    void delete(@NotNull Customer customer);

    void deleteById(@NotNull Long id);
}
