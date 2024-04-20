package by.vadzimmatsiushonak.bank.api.model.dto.response;

import by.vadzimmatsiushonak.bank.api.model.dto.base.BaseResponse;
import by.vadzimmatsiushonak.bank.api.model.entity.base.AccountType;
import by.vadzimmatsiushonak.bank.api.model.entity.base.Currency;
import by.vadzimmatsiushonak.bank.api.model.entity.base.ModelStatus;
import by.vadzimmatsiushonak.bank.api.model.entity.base.TransactionType;
import java.math.BigDecimal;

public class AccountResponse extends BaseResponse {

    public String iban;
    public String name;
    public String description;
    public AccountType type;
    public TransactionType transactionType;
    public BigDecimal amount;
    public Currency currency;
    public ModelStatus status;

}
