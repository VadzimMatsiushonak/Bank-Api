package by.vadzimmatsiushonak.bank.api.service;

import by.vadzimmatsiushonak.bank.api.model.entity.Account;
import by.vadzimmatsiushonak.bank.api.model.entity.AccountHolder;
import by.vadzimmatsiushonak.bank.api.model.entity.Bank;
import by.vadzimmatsiushonak.bank.api.model.entity.Card;
import by.vadzimmatsiushonak.bank.api.model.entity.Transaction;
import by.vadzimmatsiushonak.bank.api.model.entity.User;
import javax.validation.constraints.NotNull;

public interface ReferenceService {

    AccountHolder getAccountHolderReferenceById(@NotNull Long id);
    AccountHolder setReferences(@NotNull AccountHolder obj);

    Account getAccountReferenceById(@NotNull String id);
    Account setReferences(@NotNull Account obj);

    Bank getBankReferenceById(@NotNull Long id);
    Bank setReferences(@NotNull Bank obj);

    Card getCardReferenceById(@NotNull String id);
    Card setReferences(@NotNull Card obj);

    Transaction getTransactionReferenceById(@NotNull Long id);
    Transaction setReferences(@NotNull Transaction obj);

    User getUserReferenceById(@NotNull Long id);
    User setReferences(@NotNull User obj);

}
