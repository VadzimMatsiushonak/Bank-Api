package by.vadzimmatsiushonak.bank.api.model.entity;

import by.vadzimmatsiushonak.bank.api.model.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity(name = "BankAccounts")
public class BankAccount extends BaseEntity {

    private String title;
    private String iban;
    private String currency;
    private BigDecimal amount;
    private String type;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

    @OneToOne
    private BankCard bankCard;

    @OneToMany(mappedBy = "bankAccount", fetch = FetchType.EAGER)
    private List<BankPayment> bankPayments;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

}
