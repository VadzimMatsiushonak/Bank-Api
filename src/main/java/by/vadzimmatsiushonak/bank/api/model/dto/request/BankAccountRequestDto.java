package by.vadzimmatsiushonak.bank.api.model.dto.request;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;

public class BankAccountRequestDto {

    public String title;
    public String iban;
    public String currency;
    public BigDecimal amount;
    public String type;

    @ApiModelProperty(example = "1")
    public Long bankId;
    @ApiModelProperty(example = "1")
    public Long customerId;

}
