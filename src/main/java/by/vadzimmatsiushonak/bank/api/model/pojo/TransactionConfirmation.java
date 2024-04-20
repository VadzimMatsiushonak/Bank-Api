package by.vadzimmatsiushonak.bank.api.model.pojo;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class TransactionConfirmation {

    public String key;
    public Long transactionId;
}
