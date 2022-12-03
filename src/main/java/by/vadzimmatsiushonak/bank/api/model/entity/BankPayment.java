package by.vadzimmatsiushonak.bank.api.model.entity;

import by.vadzimmatsiushonak.bank.api.model.entity.base.BaseEntity;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "BankPayments")
public class BankPayment extends BaseEntity {

    private BigDecimal amount;
    private String currency;
    private Long recipientBankAccountId;

    @ManyToOne
    @JoinColumn(name = "bankAccount_id", nullable = false)
    private BankAccount bankAccount;

}
