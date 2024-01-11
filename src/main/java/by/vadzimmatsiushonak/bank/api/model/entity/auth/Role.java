package by.vadzimmatsiushonak.bank.api.model.entity.auth;

import java.util.Set;

public enum Role {

    ADMIN("ADMIN", Set.of(Permission.READ, Permission.WRITE)),
    TECHNICAL_USER("TECHNICAL_USER", Set.of(Permission.READ)),
    USER("USER", Set.of(Permission.READ)),
    OWNER("OWNER", Set.of(Permission.READ)),
    MANAGER("MANAGER", Set.of(Permission.READ)),
    EMPLOYEE("EMPLOYEE", Set.of(Permission.READ));

    private static final String ROLE_PREFIX = "ROLE_";
    public final String role;
    public final String authority;
    public final Set<Permission> permissions;

    Role(String role, Set<Permission> permissions) {
        this.role = role;
        this.authority = ROLE_PREFIX.concat(role);
        this.permissions = permissions;
    }

}
