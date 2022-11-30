package by.vadzimmatsiushonak.bank.api.model.dto.request;

import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;

public class BankCardRequestDto {

    public String number;
    public String cvs;
    public LocalDate expirationDate;

    @ApiModelProperty(example = "1")
    public Long bankAccountId;

}
