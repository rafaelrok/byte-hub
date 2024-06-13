package br.com.rafaelvieira.bytehub.domain.util;


import org.springframework.context.ApplicationContextException;

import java.lang.reflect.ParameterizedType;
import java.util.function.Function;

public abstract class ConvertTo<E, D> {

    protected E entity;

    /*
        Example:
        return DTO.builder()
            .id(entity.getId())
            .property1(entity.getProperty1())
            .property2(entity.getProperty2())
            .build();
     */
    public D convertToDto(E entity) {
        return null;
    }

    /*
        Example:
        return Entity.builder()
            .id(dto.getId())
            .property1(dto.getProperty1())
            .property2(dto.getProperty2())
            .build();
     */
    public E convertToEntity(D dto) {
        return null;
    }

    /**
     * Copy dto from entity
     * @param entity
     */
    protected E copyDtoToEntity(D dto) {
        Class<E> persistentClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        try {
            entity = persistentClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ApplicationContextException(String.valueOf(e));
        }

        Function<E, D> function = this::convertToDto;
        function.apply(entity);
        return entity;
    }

    public Iterable<D> convert(Iterable<E> entities) {
        return null;
    }

}
