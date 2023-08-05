package by.vadzimmatsiushonak.bank.api.v2.model.entity;

import by.vadzimmatsiushonak.bank.api.v2.model.entity.auth.Permission;
import by.vadzimmatsiushonak.bank.api.v2.model.entity.auth.Role;
import by.vadzimmatsiushonak.bank.api.v2.model.entity.base.IdEntity;
import by.vadzimmatsiushonak.bank.api.v2.model.entity.base.ModelStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import java.util.List;

@Getter
@Setter
@Entity(name = "users")
public class User extends IdEntity {

    @Column(unique = true)
    private String login;

    private String password;

    @Enumerated(EnumType.STRING)
    private ModelStatus status;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ElementCollection(targetClass = Permission.class)
    @JoinTable(name = "user_permissions", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "permission", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<Permission> permissions;

    @Embedded
    private UserDetails userDetails;

}
