package by.vadzimmatsiushonak.bank.api.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Data
@Entity(name = "BankCards")
public class BankCard extends BaseEntity{

    private String number;
    private String cvs;
    private LocalDate expirationDate;

    @OneToOne
    private BankAccount bankAccount;

}
