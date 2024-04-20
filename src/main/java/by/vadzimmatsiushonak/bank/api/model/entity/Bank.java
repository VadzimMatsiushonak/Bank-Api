package by.vadzimmatsiushonak.bank.api.model.entity;

import by.vadzimmatsiushonak.bank.api.model.entity.auth.Permission;
import by.vadzimmatsiushonak.bank.api.model.entity.base.BankTier;
import by.vadzimmatsiushonak.bank.api.model.entity.base.BankType;
import by.vadzimmatsiushonak.bank.api.model.entity.base.Currency;
import by.vadzimmatsiushonak.bank.api.model.entity.base.IdEntity;
import by.vadzimmatsiushonak.bank.api.model.entity.base.ModelStatus;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = {"accountHolder"})
@ToString(callSuper = true, exclude = {"accountHolder"})
@Entity(name = "banks")
public class Bank extends IdEntity {

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency mainCurrency;

    private BigDecimal chargeFeePercent;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BankType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BankTier tier;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ModelStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_holder_id", nullable = false)
    private AccountHolder accountHolder;

    @ElementCollection(targetClass = Permission.class)
    @JoinTable(name = "banks_permissions", joinColumns = @JoinColumn(name = "bank_id"))
    @Column(name = "permission", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<Permission> permissions;

}
