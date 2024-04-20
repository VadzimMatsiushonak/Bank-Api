package by.vadzimmatsiushonak.bank.api.repository;

import by.vadzimmatsiushonak.bank.api.model.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

}
