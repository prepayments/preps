package io.github.prepayments.service;

import io.github.prepayments.service.dto.AmortizationUpdateFileDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.prepayments.domain.AmortizationUpdateFile}.
 */
public interface AmortizationUpdateFileService {

    /**
     * Save a amortizationUpdateFile.
     *
     * @param amortizationUpdateFileDTO the entity to save.
     * @return the persisted entity.
     */
    AmortizationUpdateFileDTO save(AmortizationUpdateFileDTO amortizationUpdateFileDTO);

    /**
     * Get all the amortizationUpdateFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AmortizationUpdateFileDTO> findAll(Pageable pageable);


    /**
     * Get the "id" amortizationUpdateFile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AmortizationUpdateFileDTO> findOne(Long id);

    /**
     * Delete the "id" amortizationUpdateFile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the amortizationUpdateFile corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AmortizationUpdateFileDTO> search(String query, Pageable pageable);
}
