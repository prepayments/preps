package io.github.prepayments.service;

import io.github.prepayments.service.dto.UpdatedAmortizationEntryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.prepayments.domain.UpdatedAmortizationEntry}.
 */
public interface UpdatedAmortizationEntryService {

    /**
     * Save a updatedAmortizationEntry.
     *
     * @param updatedAmortizationEntryDTO the entity to save.
     * @return the persisted entity.
     */
    UpdatedAmortizationEntryDTO save(UpdatedAmortizationEntryDTO updatedAmortizationEntryDTO);

    /**
     * Get all the updatedAmortizationEntries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UpdatedAmortizationEntryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" updatedAmortizationEntry.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UpdatedAmortizationEntryDTO> findOne(Long id);

    /**
     * Delete the "id" updatedAmortizationEntry.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the updatedAmortizationEntry corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UpdatedAmortizationEntryDTO> search(String query, Pageable pageable);
}
