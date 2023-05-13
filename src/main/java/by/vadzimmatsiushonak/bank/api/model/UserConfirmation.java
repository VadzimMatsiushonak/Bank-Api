package by.vadzimmatsiushonak.bank.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserConfirmation {

    private final Long id;
    private final String code;

}
