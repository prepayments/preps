package io.github.prepayments.service;

import io.github.prepayments.service.dto.AmortizationEntryUpdateDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.prepayments.domain.AmortizationEntryUpdate}.
 */
public interface AmortizationEntryUpdateService {

    /**
     * Save a amortizationEntryUpdate.
     *
     * @param amortizationEntryUpdateDTO the entity to save.
     * @return the persisted entity.
     */
    AmortizationEntryUpdateDTO save(AmortizationEntryUpdateDTO amortizationEntryUpdateDTO);

    /**
     * Get all the amortizationEntryUpdates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AmortizationEntryUpdateDTO> findAll(Pageable pageable);


    /**
     * Get the "id" amortizationEntryUpdate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AmortizationEntryUpdateDTO> findOne(Long id);

    /**
     * Delete the "id" amortizationEntryUpdate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the amortizationEntryUpdate corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AmortizationEntryUpdateDTO> search(String query, Pageable pageable);
}
