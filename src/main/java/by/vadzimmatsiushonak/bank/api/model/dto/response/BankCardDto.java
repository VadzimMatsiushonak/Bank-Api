package by.vadzimmatsiushonak.bank.api.model.dto.response;

import by.vadzimmatsiushonak.bank.api.model.dto.base.BaseEntityDto;
import java.time.LocalDate;

public class BankCardDto extends BaseEntityDto {

    public String number;
    public String cvs;
    public LocalDate expirationDate;

}
