package by.vadzimmatsiushonak.bank.api.model.dto.response;

import by.vadzimmatsiushonak.bank.api.model.dto.base.BaseResponse;
import by.vadzimmatsiushonak.bank.api.model.entity.base.ModelStatus;
import java.time.LocalDate;

public class CardResponse extends BaseResponse {

    public String cardNumber;
    public String name;
    public String description;
    public LocalDate expirationDate;
    public Integer cvv;
    public Integer pinCode;
    public Integer maxUnconfirmedTransactionAmount;
    public ModelStatus status;

}
