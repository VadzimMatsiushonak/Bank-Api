package by.vadzimmatsiushonak.bank.api.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;

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
