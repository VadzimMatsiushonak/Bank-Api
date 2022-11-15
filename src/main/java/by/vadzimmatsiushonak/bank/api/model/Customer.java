package by.vadzimmatsiushonak.bank.api.model;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity(name = "Customers")
public class Customer extends BaseEntity {

    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String password;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<BankAccount> bankAccounts;


}
