package by.vadzimmatsiushonak.bank.api.model.entity.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    protected LocalDateTime createdAt;

    protected LocalDateTime modifiedAt;

    @Version
    protected Long version;

}
