package by.vadzimmatsiushonak.bank.api.listener;

import static by.vadzimmatsiushonak.bank.api.constant.MetadataConstants.ID;
import static by.vadzimmatsiushonak.bank.api.facade.impl.PaymentFacadeImpl.PAYMENT_KEY;

import by.vadzimmatsiushonak.bank.api.facade.PaymentFacade;
import by.vadzimmatsiushonak.bank.api.model.Confirmation;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CaffeineEvictionListener implements RemovalListener<Object, Object> {

    @Autowired
    @Lazy
    private PaymentFacade paymentFacade;

    @Override
    public void onRemoval(@Nullable Object key, @Nullable Object value, @NonNull RemovalCause cause) {
        if (key instanceof String && ((String) key).startsWith(PAYMENT_KEY)) {
            log.info("Expired confirmation cache PAYMENT_KEY '{}', value '{}', cause '{}'", key, value, cause);
            Long confirmTransactionId = (Long) ((Confirmation) value).getMetaData().get(ID);
            paymentFacade.withdrawUnconfirmedPayment(confirmTransactionId);
        } else {
            log.info("Expired confirmation cache key '{}', value '{}', cause '{}'", key, value, cause);
        }
    }
}
