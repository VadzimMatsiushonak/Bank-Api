package by.vadzimmatsiushonak.bank.api.v2.model.entity.auth;

public enum Permission {

    READ("READ"), WRITE("WRITE");

    private static final String ROLE_PREFIX = "PERMISSION_";
    public final String scope;
    public final String authority;

    Permission(String scope) {
        this.scope = scope;
        this.authority = ROLE_PREFIX.concat(scope);
    }
}
