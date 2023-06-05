package by.vadzimmatsiushonak.bank.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class InactiveUserException extends RuntimeException {

    public InactiveUserException(String message) {
        super(message);
    }
}