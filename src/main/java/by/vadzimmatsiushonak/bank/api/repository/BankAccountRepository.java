package by.vadzimmatsiushonak.bank.api.repository;

import by.vadzimmatsiushonak.bank.api.model.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
