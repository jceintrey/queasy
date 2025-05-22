package jerem.local.queasy.mapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Generic interface for mapping between entity and DTO. All mappers should
 * implements the interface
 *
 * @param <E> the entity type
 * @param <D> the DTO type
 */
public interface Mapper<E, D> {

    /**
     * Converts an entity to its corresponding DTO.
     *
     * @param entity the entity to convert
     * @return the corresponding DTO
     */
    D toDto(E entity);

    /**
     * Converts a DTO to its corresponding entity.
     *
     * @param dto the DTO to convert
     * @return the corresponding entity
     */
    E toEntity(D dto);

    /**
     * Converts a list of entities to a list of corresponding DTOs.
     *
     * @param entities the list of entities to convert
     * @return the list of corresponding DTOs
     */
    default List<D> toDto(List<E> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    /**
     * Converts a list of DTOs to a list of corresponding entities.
     *
     * @param dtos the list of DTOs to convert
     * @return the list of corresponding entities
     */
    default List<E> toEntity(List<D> dtos) {
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }
}