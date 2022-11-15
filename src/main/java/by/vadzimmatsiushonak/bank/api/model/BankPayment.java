package by.vadzimmatsiushonak.bank.api.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigInteger;

@Data
@Entity(name = "BankPayments")
public class BankPayment extends BaseEntity {

    private BigInteger amount;
    private String currency;
    private Long recipientBankAccountId;

    @ManyToOne
    @JoinColumn(name = "bankAccount_id", nullable = false)
    private BankAccount bankAccount;

}
