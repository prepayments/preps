package io.github.prepayments.service;

import io.github.prepayments.service.dto.AmortizationEntryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.prepayments.domain.AmortizationEntry}.
 */
public interface AmortizationEntryService {

    /**
     * Save a amortizationEntry.
     *
     * @param amortizationEntryDTO the entity to save.
     * @return the persisted entity.
     */
    AmortizationEntryDTO save(AmortizationEntryDTO amortizationEntryDTO);

    /**
     * Get all the amortizationEntries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AmortizationEntryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" amortizationEntry.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AmortizationEntryDTO> findOne(Long id);

    /**
     * Delete the "id" amortizationEntry.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the amortizationEntry corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AmortizationEntryDTO> search(String query, Pageable pageable);
}
