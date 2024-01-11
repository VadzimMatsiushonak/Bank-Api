package by.vadzimmatsiushonak.bank.api.model.dto.response;

import by.vadzimmatsiushonak.bank.api.model.dto.base.BaseIdResponse;
import by.vadzimmatsiushonak.bank.api.model.entity.auth.Permission;
import by.vadzimmatsiushonak.bank.api.model.entity.base.BankTier;
import by.vadzimmatsiushonak.bank.api.model.entity.base.BankType;
import by.vadzimmatsiushonak.bank.api.model.entity.base.Currency;
import by.vadzimmatsiushonak.bank.api.model.entity.base.ModelStatus;

import java.math.BigDecimal;
import java.util.List;

public class BankResponse extends BaseIdResponse {

    public String name;
    public String description;
    public BigDecimal amount;
    public Currency mainCurrency;
    public BankType type;
    public BankTier tier;
    public ModelStatus status;
    public List<Permission> permissions;

}
