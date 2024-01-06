package by.vadzimmatsiushonak.bank.api.model.entity;

import by.vadzimmatsiushonak.bank.api.model.entity.base.Currency;
import by.vadzimmatsiushonak.bank.api.model.entity.base.IdEntity;
import by.vadzimmatsiushonak.bank.api.model.entity.base.TransactionCategory;
import by.vadzimmatsiushonak.bank.api.model.entity.base.TransactionStatus;
import by.vadzimmatsiushonak.bank.api.model.entity.base.TransactionType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "transactions")
public class Transaction extends IdEntity {

    private BigDecimal amount;

    private BigDecimal actualAmount;

    private Currency currency;

    private BigDecimal feePercent;

    private BigDecimal feeAmount;

    private String details;

    private LocalDateTime processDate;

    @Enumerated(EnumType.STRING)
    private TransactionCategory category;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_account_id")
    private Account sender;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_card_id")
    private Card senderCard;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resipient_account_id")
    private Account recipient;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resipient_card_id")
    private Card resipientCard;

}
