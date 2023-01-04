package by.vadzimmatsiushonak.bank.api.model.entity;

import by.vadzimmatsiushonak.bank.api.model.entity.base.BaseEntity;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import lombok.Data;

@Data
@Entity(name = "Customers")
public class Customer extends BaseEntity {

    private String name;

    private String surname;

    private LocalDate dateOfBirth;

    private String phoneNumber;

    private String password;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<BankAccount> bankAccounts;

}
