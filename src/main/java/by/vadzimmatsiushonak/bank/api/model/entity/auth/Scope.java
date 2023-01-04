package by.vadzimmatsiushonak.bank.api.model.entity.auth;

public enum Scope {
    READ("READ"), WRITE("WRITE");

    private static final String ROLE_PREFIX = "SCOPE_";
    public final String scope;
    public final String authority;

    Scope(String scope) {
        this.scope = scope;
        this.authority = ROLE_PREFIX.concat(scope);
    }
}
