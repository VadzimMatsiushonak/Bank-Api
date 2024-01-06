package by.vadzimmatsiushonak.bank.api.model.v2.model.entity;

import by.vadzimmatsiushonak.bank.api.model.v2.model.entity.auth.Permission;
import by.vadzimmatsiushonak.bank.api.model.v2.model.entity.base.BankTier;
import by.vadzimmatsiushonak.bank.api.model.v2.model.entity.base.BankType;
import by.vadzimmatsiushonak.bank.api.model.v2.model.entity.base.Currency;
import by.vadzimmatsiushonak.bank.api.model.v2.model.entity.base.IdEntity;
import by.vadzimmatsiushonak.bank.api.model.v2.model.entity.base.ModelStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Entity(name = "banks")
public class Bank extends IdEntity {

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private Currency mainCurrency;

    @Enumerated(EnumType.STRING)
    private BankType type;

    @Enumerated(EnumType.STRING)
    private BankTier tier;

    @Enumerated(EnumType.STRING)
    private ModelStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_holder_id")
    private AccountHolder accountHolder;

    @ElementCollection(targetClass = Permission.class)
    @JoinTable(name = "bank_permissions", joinColumns = @JoinColumn(name = "bank_id"))
    @Column(name = "permission", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<Permission> permissions;

}
