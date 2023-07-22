package by.vadzimmatsiushonak.bank.api.model.entity;

import by.vadzimmatsiushonak.bank.api.model.entity.base.Currency;
import by.vadzimmatsiushonak.bank.api.model.entity.base.OperationType;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.*;

import lombok.Data;

@Data
@Entity(name = "bank_accounts")
public class BankAccount {

    private String title;

    @Id
    private String iban;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private OperationType type;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

    @OneToOne
    private BankCard bankCard;

    @OneToMany(mappedBy = "bankAccount", fetch = FetchType.EAGER)
    private List<BankPayment> bankPayments;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
