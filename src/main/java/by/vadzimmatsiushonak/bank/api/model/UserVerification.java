package by.vadzimmatsiushonak.bank.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserVerification {

    private final Long id;
    private final String username;
    private final Integer code;

}
