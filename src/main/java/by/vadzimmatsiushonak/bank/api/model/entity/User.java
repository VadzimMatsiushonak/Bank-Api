package by.vadzimmatsiushonak.bank.api.model.entity;

import static by.vadzimmatsiushonak.bank.api.model.entity.base.UserStatus.NEW;

import by.vadzimmatsiushonak.bank.api.model.entity.auth.Role;
import by.vadzimmatsiushonak.bank.api.model.entity.base.BaseEntity;
import by.vadzimmatsiushonak.bank.api.model.entity.base.UserStatus;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity(name = "users")
public class User extends BaseEntity {

    @Column(unique = true)
    private String login;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private String name;

    private String surname;

    private LocalDate dateOfBirth;

    @Column(unique = true)
    private String phoneNumber;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<BankAccount> bankAccounts;

    public User() {
        this.status = NEW;
    }

}
