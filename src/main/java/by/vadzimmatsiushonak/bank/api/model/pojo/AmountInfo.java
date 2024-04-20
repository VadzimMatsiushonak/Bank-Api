package by.vadzimmatsiushonak.bank.api.model.pojo;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class AmountInfo {

    public BigDecimal sentAmount;
    public BigDecimal fee;
    public BigDecimal receivedAmount;
}
