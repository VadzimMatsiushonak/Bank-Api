package by.vadzimmatsiushonak.bank.api.model;

import java.util.List;

public class ResponseDto<T> {

    public ResponseDto(T response) {
        this.response = response;
    }

    public ResponseDto(List<String> errors) {
        this.errors = errors;
    }

    public T response;
    public List<String> errors;


}
