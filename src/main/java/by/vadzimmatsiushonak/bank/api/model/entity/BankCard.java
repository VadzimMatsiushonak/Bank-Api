package by.vadzimmatsiushonak.bank.api.model.entity;

import by.vadzimmatsiushonak.bank.api.model.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Getter
@Setter
@Entity(name = "BankCards")
public class BankCard extends BaseEntity {

    private String number;
    private String cvs;
    private LocalDate expirationDate;

    @OneToOne
    private BankAccount bankAccount;

}
