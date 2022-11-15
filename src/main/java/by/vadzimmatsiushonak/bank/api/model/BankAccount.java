package by.vadzimmatsiushonak.bank.api.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;

@Data
@Entity(name = "BankAccounts")
public class BankAccount extends BaseEntity {

    private String title;
    private String iban;
    private String currency;
    private BigInteger amount;
    private String type;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

    @OneToOne
    private BankCard bankCard;

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL)
    private List<BankPayment> bankPayments;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

}
