package by.vadzimmatsiushonak.bank.api.model.dto.response;

import by.vadzimmatsiushonak.bank.api.model.dto.base.BaseEntityDto;
import java.time.LocalDate;

public class CustomerDto extends BaseEntityDto {

    public String name;
    public String surname;
    public LocalDate dateOfBirth;
    public String phoneNumber;
    public String password;

}
