package by.vadzimmatsiushonak.bank.api.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import by.vadzimmatsiushonak.bank.api.annotation.validator.ValidExpirationDateValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = ValidExpirationDateValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface ValidExpirationDate {

    String message() default "expirationDate must be one year greater than the current date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
