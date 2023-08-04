package by.vadzimmatsiushonak.bank.api.v2;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Validated
@Slf4j
@Service
public class ObjectServiceImpl implements ObjectService {
    
    private ObjectRepository repository;

    @Override
    public Object save(@NotNull Object object) {
        log.info("ObjectServiceImpl create {}", object);
        object.setId(null);

        return repository.save(object);
    }

    @Override
    public Optional<Object> findById(@NotNull Long id) {
        log.info("ObjectServiceImpl findById {}", id);

        return repository.findById(id);
    }

    @Override
    public List<Object> findAll() {
        log.info("ObjectServiceImpl findAll");

        return repository.findAll();
    }

    @Override
    public void update(@NotNull Object object) {
        log.info("ObjectServiceImpl update {}", object);

        Objects.requireNonNull(object.getId());
        repository.save(object);
    }

    @Override
    public void delete(@NotNull Object object) {
        log.info("ObjectServiceImpl delete {}", object);

        repository.delete(object);
    }

    @Override
    public void deleteById(@NotNull Long id) {
        log.info("ObjectServiceImpl deleteById {}", id);

        repository.deleteById(id);
    }
    
}
