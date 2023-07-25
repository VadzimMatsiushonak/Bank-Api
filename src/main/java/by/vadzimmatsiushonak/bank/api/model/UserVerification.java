package by.vadzimmatsiushonak.bank.api.model;

import by.vadzimmatsiushonak.bank.api.model.entity.base.BaseEntity;
import lombok.Getter;

@Getter
public class UserVerification extends VerificationEntity{
    private final BaseEntity baseEntity;

    public UserVerification(BaseEntity baseEntity, Integer code) {
        super(code);
        this.baseEntity = baseEntity;
    }
}
