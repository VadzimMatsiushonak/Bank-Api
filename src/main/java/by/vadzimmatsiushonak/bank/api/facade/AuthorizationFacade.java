package by.vadzimmatsiushonak.bank.api.facade;

import by.vadzimmatsiushonak.bank.api.model.entity.Customer;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface AuthorizationFacade {

    String registerCustomer(@NotNull Customer customer);

    Boolean confirmCustomer(@NotBlank String key, @NotBlank String code);

}
