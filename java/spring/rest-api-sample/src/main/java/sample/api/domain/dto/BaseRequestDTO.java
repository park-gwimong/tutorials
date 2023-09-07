package sample.api.domain.dto;

public abstract class BaseRequestDTO<E, T extends BaseDTO<E, T>> extends BaseDTO<E, T> {
    protected abstract E toEntity() throws IllegalAccessException;

}