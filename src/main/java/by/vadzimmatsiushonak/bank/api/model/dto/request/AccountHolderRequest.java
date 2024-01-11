package by.vadzimmatsiushonak.bank.api.model.dto.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

public class AccountHolderRequest {


    @ApiModelProperty(example = "1")
    @NotNull
    public Long userId;

    @ApiModelProperty(example = "1")
    @NotNull
    public Long bankId;

    @ApiModelProperty(notes = "IBAN structure: 2 Letter Country Code + Mobile Operator Code + First Letter Of Name + First Letter Of Surname + Mobile Number", example = "[\"BY29VM1234567\"]", required = true)
    @NotNull
    public List<String> accountIbans;

}
