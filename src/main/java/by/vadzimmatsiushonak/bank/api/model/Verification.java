package by.vadzimmatsiushonak.bank.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class Verification {

    private final Integer code;
    private final Map<Object, Object> metaData;

}
