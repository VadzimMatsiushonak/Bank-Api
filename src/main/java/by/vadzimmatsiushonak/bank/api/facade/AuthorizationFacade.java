package by.vadzimmatsiushonak.bank.api.facade;

import by.vadzimmatsiushonak.bank.api.model.entity.Customer;
import javax.validation.constraints.NotBlank;

public interface AuthorizationFacade {

    String registerCustomer(Customer customer);

    Boolean confirmCustomer(@NotBlank String key, @NotBlank String code);

}
