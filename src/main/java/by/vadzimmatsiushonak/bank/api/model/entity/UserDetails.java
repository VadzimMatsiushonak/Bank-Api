package by.vadzimmatsiushonak.bank.api.model.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class UserDetails {

    private String name;

    private String surname;

    private LocalDate dateOfBirth;

    @Column(unique = true)
    private String phoneNumber;

}
