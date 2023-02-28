package by.vadzimmatsiushonak.bank.api.model.payload;

import by.vadzimmatsiushonak.bank.api.model.payload.enums.LoggingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoggingPayload {

    private LoggingType type;
    private String message;

}
