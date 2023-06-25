package by.vadzimmatsiushonak.bank.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class VerificationNotFoundException extends RuntimeException {

    public VerificationNotFoundException(String message) {
        super(message);
    }
}
