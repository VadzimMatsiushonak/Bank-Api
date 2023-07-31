package utils;

import by.vadzimmatsiushonak.bank.api.model.entity.base.Currency;

import java.math.BigDecimal;

public class TestConstants {

    public final static String EMPTY_STRING = "";
    public final static String BLANK_STRING = " ";

    public final static String IBAN = "IBAN";
    public final static String SENDER = "SENDER_IBAN";
    public final static String RECIPIENT = "RECIPIENT_IBAN";
    public final static String ID = "ID";
    public final static String USERNAME = "USERNAME";
    public final static String PASSWORD = "PASSWORD";
    public final static String LOGIN = "LOGIN";
    public final static String WRONG_PASSWORD = "WRONG_PASSWORD";
    public final static String TOKEN = "TOKEN";
    public final static String PHONENUMBER = "PHONENUMBER";
    public final static String KEY = "KEY";
    public final static String CODE = "CODE";

    public final static Integer CODE_INT = 1234;
    public final static Integer WRONG_CODE_INT = 4321;

    public final static Long ID_LONG = 1L;
    public final static Long AMOUNT_LONG = 1L;

    public final static BigDecimal AMOUNT_BD = new BigDecimal(AMOUNT_LONG);

    public final static Currency CURRENCY = Currency.USD;

}
