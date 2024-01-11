package by.vadzimmatsiushonak.bank.api.model.dto.request;

import by.vadzimmatsiushonak.bank.api.annotation.ValidExpirationDate;
import by.vadzimmatsiushonak.bank.api.model.entity.base.ModelStatus;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class CardRequest {

    @ApiModelProperty(notes = "'cardNumber'.length == 16 value must be 16 characters long", example = "1234567890123456", required = true)
    @Size(min = 16, max = 16)
    @NotNull
    public String cardNumber;

    @ApiModelProperty(required = true)
    @NotNull
    public String name;

    public String description;

    @ApiModelProperty(notes = "'expirationDate' value must be one year greater than the current date", example = "2030-01-01", required = true)
    @ValidExpirationDate
    @NotNull
    public LocalDate expirationDate;

    @ApiModelProperty(notes = "'cvv'.length == 3 value must be 3 characters long", example = "123", required = true)
    @Size(min = 3, max = 3)
    @NotNull
    public Integer cvv;

    @ApiModelProperty(required = true)
    @NotNull
    public Integer pinCode;

    @ApiModelProperty(required = true)
    @NotNull
    public Integer maxUnconfirmedTransactionAmount;

    public ModelStatus status;

    @ApiModelProperty(notes = "IBAN structure: 2 Letter Country Code + Mobile Operator Code + First Letter Of Name + First Letter Of Surname + Mobile Number", example = "BY29VM1234567", required = true)
    @NotNull
    public String accountIban;

    @ApiModelProperty(example = "1", required = true)
    @NotNull
    public Long bankId;

}
