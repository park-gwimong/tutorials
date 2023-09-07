package sample.api.domain.mapper;

public interface EntityMapper<E, D> {
    E toEntity(final D dto);

    D toDto(final E entity);
}
