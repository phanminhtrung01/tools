package com.pmt.tool.component;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Getter
@Setter
public class Converter<T, S> {

    private final T entity = null;
    private final S dto = null;

    ModelMapper modelMapper = new ModelMapper();

    public S entityToDto(T entity, Class<S> dtoClass) {
        modelMapper.map(entity, dtoClass);

        return getDto();
    }

    public List<S> entityToDto(@NotNull List<T> entityList, Class<S> dtoClass) {

        return entityList
                .stream()
                .map(entity -> modelMapper.map(entity, dtoClass))
                .collect(Collectors.toList());
    }

    public Optional<S> entityToDto(@NotNull Optional<T> entityOptional, Class<S> dtoClass) {

        return entityOptional.map(entity -> modelMapper.map(entity, dtoClass));
    }

    public T dtoToEntity(S dto, Class<T> entityClass) {
        modelMapper.map(dto, entityClass);

        return getEntity();
    }

    /*public List<T> dtoToEntity(@NotNull List<S> dtoList, Class<T> entityClass) {
        List<T> tList = dtoList
                .stream()
                .map(dto -> modelMapper.map(dto, entityClass))
                .collect(Collectors.toList());

        setEntityList(tList);

        return getEntityList();
    }*/

}
