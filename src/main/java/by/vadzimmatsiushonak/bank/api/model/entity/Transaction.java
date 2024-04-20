package by.vadzimmatsiushonak.bank.api.model.entity;

import by.vadzimmatsiushonak.bank.api.model.entity.base.Currency;
import by.vadzimmatsiushonak.bank.api.model.entity.base.IdEntity;
import by.vadzimmatsiushonak.bank.api.model.entity.base.TransactionCategory;
import by.vadzimmatsiushonak.bank.api.model.entity.base.TransactionStatus;
import by.vadzimmatsiushonak.bank.api.model.entity.base.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = {"sender", "senderCard", "recipient", "recipientCard"})
@ToString(callSuper = true, exclude = {"sender", "senderCard", "recipient", "recipientCard"})
@Entity(name = "transactions")
public class Transaction extends IdEntity {

    @Column(nullable = false)
    private BigDecimal amount;

    private BigDecimal actualAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;

    private BigDecimal feePercent;

    private BigDecimal feeAmount;

    private String details;

    private LocalDateTime processDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_account_iban", nullable = false)
    private Account sender;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_card_number")
    private Card senderCard;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_account_iban", nullable = false)
    private Account recipient;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_card_number")
    private Card recipientCard;

}
