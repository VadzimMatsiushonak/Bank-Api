package by.vadzimmatsiushonak.bank.api.util;

import by.vadzimmatsiushonak.bank.api.exception.ConfirmationNotFoundException;
import by.vadzimmatsiushonak.bank.api.exception.EntityNotFoundException;
import by.vadzimmatsiushonak.bank.api.exception.InsufficientFundsException;
import by.vadzimmatsiushonak.bank.api.exception.InvalidConfirmationException;
import by.vadzimmatsiushonak.bank.api.exception.WrongDataException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionUtils {

    private static final Properties props = new Properties();

    static {
        try (InputStream input = ExceptionUtils.class.getClassLoader().getResourceAsStream("exception.properties")) {
            props.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        props.values().forEach(System.out::println);
    }

    private static String format(String pattern, Object... values) {
        return MessageFormat.format(props.getProperty(pattern), values);
    }

    public static void throw_EntityNotFoundException(Object... values) {
        throw new_EntityNotFoundException(values);
    }

    public static EntityNotFoundException new_EntityNotFoundException(Object... values) {
        return new EntityNotFoundException(format("EntityNotFoundException", values));
    }

    public static void throw_InsufficientFundsException(Object... values) throws InsufficientFundsException {
        throw new_InsufficientFundsException(values);
    }

    public static InsufficientFundsException new_InsufficientFundsException(Object... values) {
        return new InsufficientFundsException(format("InsufficientFundsExceptions", values));
    }

    public static void throw_WrongDataException(Object... values) {
        throw new_WrongDataException(values);
    }

    public static WrongDataException new_WrongDataException(Object... values) {
        return new WrongDataException(format("WrongDataException", values));
    }

    public static void throw_ConfirmationNotFoundException(Object... values) {
        throw new_ConfirmationNotFoundException(values);
    }

    public static ConfirmationNotFoundException new_ConfirmationNotFoundException(Object... values) {
        return new ConfirmationNotFoundException(format("ConfirmationNotFoundException", values));
    }

    public static void throw_InvalidConfirmationException(Object... values) {
        throw new_InvalidConfirmationException(values);
    }

    public static InvalidConfirmationException new_InvalidConfirmationException(Object... values) {
        return new InvalidConfirmationException(format("InvalidConfirmationException", values));
    }


}
