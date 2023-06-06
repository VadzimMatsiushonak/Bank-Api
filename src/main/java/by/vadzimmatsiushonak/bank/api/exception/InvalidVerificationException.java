package by.vadzimmatsiushonak.bank.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidVerificationException extends RuntimeException {

    public InvalidVerificationException(String message) {
        super(message);
    }
}

