package by.vadzimmatsiushonak.bank.api.model;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.math.BigInteger;
import java.util.List;

@Data
@Entity(name = "Banks")
public class Bank extends BaseEntity{

    private String title;
    private BigInteger amount;

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL)
    private List<BankAccount> bankAccounts;

}
