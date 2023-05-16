package by.vadzimmatsiushonak.bank.api.facade;

import static by.vadzimmatsiushonak.bank.api.util.NumberUtils.VERIFICATION_MAX_VALUE;
import static by.vadzimmatsiushonak.bank.api.util.NumberUtils.VERIFICATION_MIN_VALUE;

import by.vadzimmatsiushonak.bank.api.model.entity.Customer;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface AuthorizationFacade {

    String registerCustomer(@NotNull Customer customer);

    Boolean confirmCustomer(@NotBlank String key,
        @Min(VERIFICATION_MIN_VALUE) @Max(VERIFICATION_MAX_VALUE) Integer code);

}
