package by.vadzimmatsiushonak.bank.api.model.entity.base;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@MappedSuperclass
public abstract class BaseEntity {

    @CreationTimestamp
    protected LocalDateTime createdAt;

    @UpdateTimestamp
    protected LocalDateTime modifiedAt;

    @Version
    protected Long version;

}
