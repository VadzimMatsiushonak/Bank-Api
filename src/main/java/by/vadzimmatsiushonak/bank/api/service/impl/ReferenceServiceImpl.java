package by.vadzimmatsiushonak.bank.api.service.impl;

import static by.vadzimmatsiushonak.bank.api.util.EntityUtils.getFieldOptional;
import static by.vadzimmatsiushonak.bank.api.util.EntityUtils.getFieldsOptional;

import by.vadzimmatsiushonak.bank.api.model.entity.Account;
import by.vadzimmatsiushonak.bank.api.model.entity.AccountHolder;
import by.vadzimmatsiushonak.bank.api.model.entity.Bank;
import by.vadzimmatsiushonak.bank.api.model.entity.Card;
import by.vadzimmatsiushonak.bank.api.model.entity.Transaction;
import by.vadzimmatsiushonak.bank.api.model.entity.User;
import by.vadzimmatsiushonak.bank.api.repository.AccountHolderRepository;
import by.vadzimmatsiushonak.bank.api.repository.AccountRepository;
import by.vadzimmatsiushonak.bank.api.repository.BankRepository;
import by.vadzimmatsiushonak.bank.api.repository.CardRepository;
import by.vadzimmatsiushonak.bank.api.repository.TransactionRepository;
import by.vadzimmatsiushonak.bank.api.repository.UserRepository;
import by.vadzimmatsiushonak.bank.api.service.ReferenceService;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
@RequiredArgsConstructor
@Slf4j
public class ReferenceServiceImpl implements ReferenceService {

    private final AccountHolderRepository accountHolderRepository;
    private final AccountRepository accountRepository;
    private final BankRepository bankRepository;
    private final CardRepository cardRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Override
    public AccountHolder getAccountHolderReferenceById(@NotNull Long id) {
        log.info("ReferenceFinderServiceImpl getAccountHolderReferenceById {}", id);
        return accountHolderRepository.getReferenceById(id);
    }

    @Override
    public AccountHolder setReferences(AccountHolder obj) {
        getFieldOptional(obj.getBank(), Bank::getId)
            .ifPresent(id -> obj.setBank(getBankReferenceById(id)));
        getFieldOptional(obj.getUser(), User::getId)
            .ifPresent(id -> obj.setUser(getUserReferenceById(id)));
        getFieldsOptional(obj.getAccounts(), Account::getIban)
            .ifPresent(idList -> obj.setAccounts(
                idList.stream()
                    .map(this::getAccountReferenceById)
                    .collect(Collectors.toList())
            ));
        return obj;
    }

    @Override
    public Account getAccountReferenceById(@NotNull String id) {
        log.info("ReferenceFinderServiceImpl getAccountReferenceById {}", id);
        return accountRepository.getReferenceById(id);
    }

    @Override
    public Account setReferences(Account obj) {
        getFieldOptional(obj.getBank(), Bank::getId)
            .ifPresent(id -> obj.setBank(getBankReferenceById(id)));
        getFieldOptional(obj.getAccountHolder(), AccountHolder::getId)
            .ifPresent(id -> obj.setAccountHolder(getAccountHolderReferenceById(id)));
        return obj;
    }

    @Override
    public Bank getBankReferenceById(@NotNull Long id) {
        log.info("ReferenceFinderServiceImpl getBankReferenceById {}", id);
        return bankRepository.getReferenceById(id);
    }

    @Override
    public Bank setReferences(Bank obj) {
        getFieldOptional(obj.getAccountHolder(), AccountHolder::getId)
            .ifPresent(id -> obj.setAccountHolder(getAccountHolderReferenceById(id)));
        return obj;
    }

    @Override
    public Card getCardReferenceById(@NotNull String id) {
        log.info("ReferenceFinderServiceImpl getCardReferenceById {}", id);
        return cardRepository.getReferenceById(id);
    }

    @Override
    public Card setReferences(Card obj) {
        getFieldOptional(obj.getAccount(), Account::getIban)
            .ifPresent(id -> obj.setAccount(getAccountReferenceById(id)));
        getFieldOptional(obj.getBank(), Bank::getId)
            .ifPresent(id -> obj.setBank(getBankReferenceById(id)));
        return obj;
    }

    @Override
    public Transaction getTransactionReferenceById(@NotNull Long id) {
        log.info("ReferenceFinderServiceImpl getTransactionReferenceById {}", id);
        return transactionRepository.getReferenceById(id);
    }

    @Override
    public Transaction setReferences(Transaction obj) {
        getFieldOptional(obj.getSender(), Account::getIban)
            .ifPresent(id -> obj.setSender(getAccountReferenceById(id)));
        getFieldOptional(obj.getSenderCard(), Card::getCardNumber)
            .ifPresent(id -> obj.setSenderCard(getCardReferenceById(id)));
        getFieldOptional(obj.getRecipient(), Account::getIban)
            .ifPresent(id -> obj.setRecipient(getAccountReferenceById(id)));
        getFieldOptional(obj.getRecipientCard(), Card::getCardNumber)
            .ifPresent(id -> obj.setRecipientCard(getCardReferenceById(id)));
        return obj;
    }

    @Override
    public User getUserReferenceById(@NotNull Long id) {
        log.info("ReferenceFinderServiceImpl getUserReferenceById {}", id);
        return userRepository.getReferenceById(id);
    }

    @Override
    public User setReferences(User obj) {
        return obj;
    }
}
