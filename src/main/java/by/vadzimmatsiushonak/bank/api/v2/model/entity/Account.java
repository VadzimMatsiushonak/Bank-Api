package by.vadzimmatsiushonak.bank.api.v2.model.entity;

import by.vadzimmatsiushonak.bank.api.v2.model.entity.base.AccountType;
import by.vadzimmatsiushonak.bank.api.v2.model.entity.base.BaseEntity;
import by.vadzimmatsiushonak.bank.api.v2.model.entity.base.Currency;
import by.vadzimmatsiushonak.bank.api.v2.model.entity.base.ModelStatus;
import by.vadzimmatsiushonak.bank.api.v2.model.entity.base.TransactionType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity(name = "accounts")
public class Account extends BaseEntity {

    @Id
    private String iban;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private AccountType type;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private ModelStatus status;

    @ManyToOne
    @JoinColumn(name = "account_holder_id", nullable = false)
    private AccountHolder accountHolder;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

}
