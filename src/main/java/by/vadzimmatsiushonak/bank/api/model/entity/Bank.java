package by.vadzimmatsiushonak.bank.api.model.entity;

import by.vadzimmatsiushonak.bank.api.model.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity(name = "Banks")
public class Bank extends BaseEntity {

    private String title;
    private BigDecimal amount;

    @OneToMany(mappedBy = "bank", fetch = FetchType.EAGER)
    private List<BankAccount> bankAccounts;

}
