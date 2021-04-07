package sel.exicon.mvc_thymeleaf_project.converter;

import java.util.Collection;

public interface Converter<T, U> {
    T toModel(U dto);

    U toDTO(T model);

    Collection<T> toModels(Collection<U> collection);

    Collection<U> toDTos(Collection<T> collection);

}
