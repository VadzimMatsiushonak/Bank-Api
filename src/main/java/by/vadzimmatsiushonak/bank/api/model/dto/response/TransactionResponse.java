package by.vadzimmatsiushonak.bank.api.model.dto.response;

import by.vadzimmatsiushonak.bank.api.model.dto.base.BaseIdResponse;
import by.vadzimmatsiushonak.bank.api.model.entity.base.Currency;
import by.vadzimmatsiushonak.bank.api.model.entity.base.TransactionCategory;
import by.vadzimmatsiushonak.bank.api.model.entity.base.TransactionStatus;
import by.vadzimmatsiushonak.bank.api.model.entity.base.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponse extends BaseIdResponse {

    public BigDecimal amount;
    public BigDecimal actualAmount;
    public Currency currency;
    public BigDecimal feePercent;
    public BigDecimal feeAmount;
    public String details;
    public LocalDateTime processDate;
    public TransactionCategory category;
    public TransactionType type;
    public TransactionStatus status;

}
