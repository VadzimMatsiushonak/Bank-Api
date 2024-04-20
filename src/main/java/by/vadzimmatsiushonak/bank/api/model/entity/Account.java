package by.vadzimmatsiushonak.bank.api.model.entity;

import by.vadzimmatsiushonak.bank.api.model.entity.base.AccountType;
import by.vadzimmatsiushonak.bank.api.model.entity.base.BaseEntity;
import by.vadzimmatsiushonak.bank.api.model.entity.base.Currency;
import by.vadzimmatsiushonak.bank.api.model.entity.base.ModelStatus;
import by.vadzimmatsiushonak.bank.api.model.entity.base.TransactionType;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = {"accountHolder", "bank"})
@ToString(callSuper = true, exclude = {"accountHolder", "bank"})
@Entity(name = "accounts")
public class Account extends BaseEntity {

    @Id
    @Column(nullable = false)
    private String iban;

    @Column(nullable = false)
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ModelStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_holder_id", nullable = false)
    private AccountHolder accountHolder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

}
