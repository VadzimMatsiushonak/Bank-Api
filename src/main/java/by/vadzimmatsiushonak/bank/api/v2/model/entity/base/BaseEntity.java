package by.vadzimmatsiushonak.bank.api.v2.model.entity.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    protected LocalDateTime createdAt;

    protected LocalDateTime modifiedAt;

    protected Long version;
}
