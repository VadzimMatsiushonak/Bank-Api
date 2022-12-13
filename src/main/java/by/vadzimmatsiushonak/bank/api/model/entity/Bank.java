package by.vadzimmatsiushonak.bank.api.model.entity;

import by.vadzimmatsiushonak.bank.api.model.entity.base.BaseEntity;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Banks")
public class Bank extends BaseEntity {

    private String title;

    private BigDecimal amount;

    @OneToMany(mappedBy = "bank", fetch = FetchType.EAGER)
    private List<BankAccount> bankAccounts;

}
