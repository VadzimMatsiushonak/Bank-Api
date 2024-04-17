package by.vadzimmatsiushonak.bank.api.model.dto.request;

import by.vadzimmatsiushonak.bank.api.model.entity.auth.Permission;
import by.vadzimmatsiushonak.bank.api.model.entity.base.BankTier;
import by.vadzimmatsiushonak.bank.api.model.entity.base.BankType;
import by.vadzimmatsiushonak.bank.api.model.entity.base.Currency;
import by.vadzimmatsiushonak.bank.api.model.entity.base.ModelStatus;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public class BankRequest {

    @ApiModelProperty(required = true)
    @NotNull
    public String name;

    public String description;

    @ApiModelProperty(notes = "'amount' >= 1.0 value must be higher or equal to 1.0", example = "1.0", required = true)
    @DecimalMin(value = "1.0")
    @NotNull
    public BigDecimal amount;

    @ApiModelProperty(example = "USD", required = true)
    @NotNull
    public Currency mainCurrency;

    @ApiModelProperty(example = "10.00")
    public BigDecimal chargeFee;

    @ApiModelProperty(example = "BUSINESS", required = true)
    @NotNull
    public BankType type;

    @ApiModelProperty(example = "INTERNATIONAL", required = true)
    @NotNull
    public BankTier tier;

    public ModelStatus status;

    public List<Permission> permissions;

    @ApiModelProperty(example = "1", required = true)
    @NotNull
    public Long accountHolderId;

}
