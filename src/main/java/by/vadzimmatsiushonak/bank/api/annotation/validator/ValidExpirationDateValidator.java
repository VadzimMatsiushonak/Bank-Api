package by.vadzimmatsiushonak.bank.api.annotation.validator;

import by.vadzimmatsiushonak.bank.api.annotation.ValidExpirationDate;
import java.time.LocalDate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidExpirationDateValidator implements ConstraintValidator<ValidExpirationDate, LocalDate> {

    @Override
    public void initialize(ValidExpirationDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        LocalDate minExpectedDate = LocalDate.now().plusYears(1).minusDays(1);
        return value.isAfter(minExpectedDate);
    }
}
