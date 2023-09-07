package sample.api.domain.dto;

import org.springframework.data.domain.Page;

public abstract class BaseResponseDTO<E, T extends BaseDTO<E, T>> extends BaseDTO<E, T> {
    protected abstract T fromEntity(E e) throws IllegalAccessException;

    protected abstract Page<T> toDtoList(Page<E> list);
}
