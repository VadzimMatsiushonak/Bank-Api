package by.vadzimmatsiushonak.bank.api.model.entity;

import by.vadzimmatsiushonak.bank.api.model.entity.base.IdEntity;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = {"user", "bank", "accounts"})
@ToString(callSuper = true, exclude = {"user", "bank", "accounts"})
@Entity(name = "account_holders")
public class AccountHolder extends IdEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @OneToMany(mappedBy = "accountHolder", fetch = FetchType.LAZY)
    private List<Account> accounts;

}
