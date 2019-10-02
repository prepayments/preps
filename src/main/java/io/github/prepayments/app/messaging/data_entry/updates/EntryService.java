package io.github.prepayments.app.messaging.data_entry.updates;

import java.util.Optional;

/**
 * Interface for general entry services
 *
 * @param <I> Data type for identifier
 * @param <T> Type of data transfer object
 */
public interface EntryService<I,T> {

    /**
     * Finds T with id of type I
     *
     * @param id
     * @return
     */
    Optional<T> findOne(I id);

    /**
     * Saves dto of type T and returns the persisted dto from
     * repository
     *
     * @param dto
     * @return
     */
    T save(T dto);
}
