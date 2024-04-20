package by.vadzimmatsiushonak.bank.api.repository;

import by.vadzimmatsiushonak.bank.api.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
