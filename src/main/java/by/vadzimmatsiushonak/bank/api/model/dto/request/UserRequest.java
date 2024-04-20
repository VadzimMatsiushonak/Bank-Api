package by.vadzimmatsiushonak.bank.api.model.dto.request;

import by.vadzimmatsiushonak.bank.api.model.entity.auth.Permission;
import by.vadzimmatsiushonak.bank.api.model.entity.auth.Role;
import by.vadzimmatsiushonak.bank.api.model.entity.base.ModelStatus;
import io.swagger.annotations.ApiModelProperty;
import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRequest {

    @ApiModelProperty(required = true)
    @NotNull
    public String login;

    @ApiModelProperty(notes = "'password' must be between 8 and 50 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and one special character.",
        example = "MyP@ssw0rd123",
        required = true)
    @Size(min = 8, max = 50)
    // TODO: Add password pattern
//    @Pattern(
//        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
//        message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character"
//    )
    @NotNull
    public String password;

    public ModelStatus status;

    @ApiModelProperty(example = "USER", required = true)
    @NotNull
    public Role role;

    public Set<Permission> permissions;

    public UserDetailsRequest userDetails;

}
