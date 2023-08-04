package by.vadzimmatsiushonak.bank.api.v2;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface ObjectService {

    Object save(@NotNull Object object);

    Optional<Object> findById(@NotNull Long id);

    List<Object> findAll();

    void update(@NotNull Object object);

    void delete(@NotNull Object object);

    void deleteById(@NotNull Long id);

}
