package by.vadzimmatsiushonak.bank.api.service.impl;

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
public class BankPaymentServiceImpl  {
//
//    private final TransactionRepository repository;
//
//    @Override
//    public BankPayment save(@NotNull BankPayment bankPayment) {
//        log.info("BankPaymentServiceImpl create {}", bankPayment);
//        bankPayment.setId(null);
//
//        return repository.save(bankPayment);
//    }
//
//    @Override
//    public Optional<BankPayment> findById(@NotNull Long id) {
//        log.info("BankPaymentServiceImpl findById {}", id);
//
//        return repository.findById(id);
//    }
//
//    @Override
//    public List<BankPayment> findAll() {
//        log.info("BankPaymentServiceImpl findAll");
//
//        return repository.findAll();
//    }
//
//    @Override
//    public void update(@NotNull BankPayment bankPayment) {
//        log.info("BankPaymentServiceImpl update {}", bankPayment);
//
//        Objects.requireNonNull(bankPayment.getId());
//        repository.save(bankPayment);
//    }
//
//    @Override
//    public void delete(@NotNull BankPayment bankPayment) {
//        log.info("BankPaymentServiceImpl delete {}", bankPayment);
//
//        repository.delete(bankPayment);
//    }
//
//    @Override
//    public void deleteById(@NotNull Long id) {
//        log.info("BankPaymentServiceImpl deleteById {}", id);
//
//        repository.deleteById(id);
//    }

}
