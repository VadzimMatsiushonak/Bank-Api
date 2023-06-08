package by.vadzimmatsiushonak.bank.api.model.entity;

import by.vadzimmatsiushonak.bank.api.model.entity.base.BaseEntity;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "bank_cards")
public class BankCard extends BaseEntity {

    private String number;

    private String cvs;

    private LocalDate expirationDate;

    @OneToOne
    @JoinColumn(name = "bank_account_id", nullable = false)
    private BankAccount bankAccount;

}
