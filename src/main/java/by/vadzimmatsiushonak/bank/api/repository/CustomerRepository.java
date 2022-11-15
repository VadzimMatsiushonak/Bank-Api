package by.vadzimmatsiushonak.bank.api.repository;

import by.vadzimmatsiushonak.bank.api.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
