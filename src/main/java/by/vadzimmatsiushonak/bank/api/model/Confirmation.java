package by.vadzimmatsiushonak.bank.api.model;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Confirmation {

    private final Integer code;
    private final Map<Object, Object> metaData;

}
