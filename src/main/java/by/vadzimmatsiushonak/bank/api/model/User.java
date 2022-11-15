package by.vadzimmatsiushonak.bank.api.model;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity(name = "Users")
public class User extends BaseEntity {

    private String login;
    private String password;
    private String role;

}
