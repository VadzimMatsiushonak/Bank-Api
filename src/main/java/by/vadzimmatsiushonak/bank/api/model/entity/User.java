package by.vadzimmatsiushonak.bank.api.model.entity;

import by.vadzimmatsiushonak.bank.api.model.entity.auth.Permission;
import by.vadzimmatsiushonak.bank.api.model.entity.auth.Role;
import by.vadzimmatsiushonak.bank.api.model.entity.base.IdEntity;
import by.vadzimmatsiushonak.bank.api.model.entity.base.ModelStatus;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity(name = "users")
public class User extends IdEntity {

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ModelStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @ElementCollection(targetClass = Permission.class)
    @JoinTable(name = "users_permissions", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "permission", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Permission> permissions;

    @Embedded
    private UserDetails userDetails;

}
