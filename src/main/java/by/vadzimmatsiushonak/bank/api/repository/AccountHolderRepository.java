package by.vadzimmatsiushonak.bank.api.repository;

import by.vadzimmatsiushonak.bank.api.model.entity.AccountHolder;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long> {

    Optional<AccountHolder> findByUserLogin(String login);

    Optional<AccountHolder> findByUserId(Long id);
}
