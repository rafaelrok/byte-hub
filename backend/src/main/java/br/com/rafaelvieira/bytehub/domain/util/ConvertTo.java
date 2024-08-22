package br.com.rafaelvieira.bytehub.domain.util;

import org.springframework.context.ApplicationContextException;

import java.lang.reflect.ParameterizedType;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class ConvertTo<E, D> {

    /*
        Convert entity to DTO. Implement this method in subclasses.
     */
    public abstract Optional<D> convertToDto(E entity);

    /*
        Convert DTO to entity. Implement this method in subclasses.
     */
    public abstract Optional<E> convertToEntity(D dto);

    /*
        Convert Iterable<E> to Iterable<D>.
     */
    public Iterable<D> convert(Iterable<E> entities) {
        return StreamSupport.stream(entities.spliterator(), false)
                .map(this::convertToDto)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    /*
        Copy DTO to entity. This method is protected as it's intended to be used internally by subclasses.
     */
    protected Optional<E> copyDtoToEntity(D dto) {
        Class<E> persistentClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        E entity;
        try {
            entity = persistentClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new ApplicationContextException("Failed to create a new instance of " + persistentClass.getSimpleName(), e);
        }

        return convertToEntity(dto).map(e -> {
            // Here you can copy properties from dto to entity if needed
            return e;
        });
    }
}