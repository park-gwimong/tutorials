package sample.api.domain.mapper;

public interface EntityMapper<E, D> {
    default E toEntity(final D dto) throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    default D toDto(final E entity) throws IllegalAccessException {
        throw new IllegalAccessException();
    }
}
