package by.vadzimmatsiushonak.bank.api.util;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EntityUtils {

    public static <T, ID> Optional<ID> getFieldOptional(T entity, Function<T, ID> extractor) {
        return Optional.ofNullable(entity).map(extractor);
    }

    public static <T, ID> Optional<List<ID>> getFieldsOptional(List<T> entities, Function<T, ID> extractor) {
        return Optional.ofNullable(entities).map(list -> list.stream().map(extractor).collect(Collectors.toList()));
    }

}
