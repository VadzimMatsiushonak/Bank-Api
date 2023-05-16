package by.vadzimmatsiushonak.bank.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ConfirmationNotFoundException extends RuntimeException {

    public ConfirmationNotFoundException(String message) {
        super(message);
    }
}
