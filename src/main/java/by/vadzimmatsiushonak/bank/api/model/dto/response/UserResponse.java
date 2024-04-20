package by.vadzimmatsiushonak.bank.api.model.dto.response;

import by.vadzimmatsiushonak.bank.api.model.dto.base.BaseIdResponse;
import by.vadzimmatsiushonak.bank.api.model.entity.UserDetails;
import by.vadzimmatsiushonak.bank.api.model.entity.auth.Permission;
import by.vadzimmatsiushonak.bank.api.model.entity.auth.Role;
import by.vadzimmatsiushonak.bank.api.model.entity.base.ModelStatus;
import java.util.Set;

public class UserResponse extends BaseIdResponse {

    public String login;
    public String password;
    public ModelStatus status;
    public Role role;
    public Set<Permission> permissions;
    public UserDetails userDetails;

}
