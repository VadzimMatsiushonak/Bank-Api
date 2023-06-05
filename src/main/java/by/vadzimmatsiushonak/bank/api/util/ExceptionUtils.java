package by.vadzimmatsiushonak.bank.api.util;

import by.vadzimmatsiushonak.bank.api.exception.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionUtils {

    private static final Properties props = new Properties();

    static {
        try (InputStream input = ExceptionUtils.class.getClassLoader()
                .getResourceAsStream("exception.properties")) {
            props.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        props.values().forEach(System.out::println);
    }

    private static String format(String pattern, Object... values) {
        return MessageFormat.format(props.getProperty(pattern), values);
    }

    public static EntityNotFoundException new_EntityNotFoundException(Object... values) {
        return new EntityNotFoundException(format("EntityNotFoundException", values));
    }

    public static InsufficientFundsException new_InsufficientFundsException(Object... values) {
        return new InsufficientFundsException(format("InsufficientFundsExceptions", values));
    }

    public static WrongDataException new_WrongDataException(Object... values) {
        return new WrongDataException(format("WrongDataException", values));
    }

    public static ConfirmationNotFoundException new_ConfirmationNotFoundException(
            Object... values) {
        return new ConfirmationNotFoundException(format("ConfirmationNotFoundException", values));
    }

    public static InvalidConfirmationException new_InvalidConfirmationException(Object... values) {
        return new InvalidConfirmationException(format("InvalidConfirmationException", values));
    }

    public static InvalidCredentialsException new_InvalidCredentialsException(Object... values) {
        return new InvalidCredentialsException(format("InvalidCredentialsException", values));
    }

    public static UserNotFoundException new_UserNotFoundException(Object... values) {
        return new UserNotFoundException(format("UserNotFoundException", values));
    }

    public static InactiveUserException new_InactiveUserException(Object... values) {
        return new InactiveUserException(format("InactiveUserException", values));
    }

}
