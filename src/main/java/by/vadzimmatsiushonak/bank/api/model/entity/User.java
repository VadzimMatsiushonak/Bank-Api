package by.vadzimmatsiushonak.bank.api.model.entity;

import static by.vadzimmatsiushonak.bank.api.model.entity.base.UserStatus.NEW;

import by.vadzimmatsiushonak.bank.api.model.entity.auth.Role;
import by.vadzimmatsiushonak.bank.api.model.entity.base.BaseEntity;
import by.vadzimmatsiushonak.bank.api.model.entity.base.UserStatus;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "users")
public class User extends BaseEntity {

    @Column(unique = true)
    private String login;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    public User() {
    }

    public User(Customer customer) {
        this.login = customer.getPhoneNumber();
        this.password = customer.getPassword();
        this.status = NEW;
    }

}
