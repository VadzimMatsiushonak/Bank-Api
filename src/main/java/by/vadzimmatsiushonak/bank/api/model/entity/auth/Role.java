package by.vadzimmatsiushonak.bank.api.model.entity.auth;

import java.util.Set;

public enum Role {
    ADMIN("ADMIN", Set.of(Scope.READ, Scope.WRITE)),
    TECHNICAL_USER("TECHNICAL_USER", Set.of(Scope.READ));

    private static final String ROLE_PREFIX = "ROLE_";
    public final String role;
    public final String authority;
    public final Set<Scope> scopes;

    Role(String role, Set<Scope> scopes) {
        this.role = role;
        this.authority = ROLE_PREFIX.concat(role);
        this.scopes = scopes;
    }
}
