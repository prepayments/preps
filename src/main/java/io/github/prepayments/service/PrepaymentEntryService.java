package io.github.prepayments.service;

import io.github.prepayments.service.dto.PrepaymentEntryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.prepayments.domain.PrepaymentEntry}.
 */
public interface PrepaymentEntryService {

    /**
     * Save a prepaymentEntry.
     *
     * @param prepaymentEntryDTO the entity to save.
     * @return the persisted entity.
     */
    PrepaymentEntryDTO save(PrepaymentEntryDTO prepaymentEntryDTO);

    /**
     * Get all the prepaymentEntries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PrepaymentEntryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" prepaymentEntry.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PrepaymentEntryDTO> findOne(Long id);

    /**
     * Delete the "id" prepaymentEntry.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the prepaymentEntry corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PrepaymentEntryDTO> search(String query, Pageable pageable);
}
