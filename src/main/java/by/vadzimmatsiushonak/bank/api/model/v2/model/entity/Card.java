package by.vadzimmatsiushonak.bank.api.model.v2.model.entity;

import by.vadzimmatsiushonak.bank.api.model.v2.model.entity.base.BaseEntity;
import by.vadzimmatsiushonak.bank.api.model.v2.model.entity.base.ModelStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Getter
@Setter
@Entity(name = "card")
public class Card extends BaseEntity {

    @Id
    private String cardNumber;

    private String name;

    private String description;

    private LocalDate expirationDate;

    private Integer cvv;

    private Integer pinCode;

    private Integer maxUnconfirmedTransactionAmount;

    @Enumerated(EnumType.STRING)
    private ModelStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id")
    private Bank bank;

}
