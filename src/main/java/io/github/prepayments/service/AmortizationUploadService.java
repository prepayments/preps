package io.github.prepayments.service;

import io.github.prepayments.service.dto.AmortizationUploadDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.prepayments.domain.AmortizationUpload}.
 */
public interface AmortizationUploadService {

    /**
     * Save a amortizationUpload.
     *
     * @param amortizationUploadDTO the entity to save.
     * @return the persisted entity.
     */
    AmortizationUploadDTO save(AmortizationUploadDTO amortizationUploadDTO);

    /**
     * Get all the amortizationUploads.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AmortizationUploadDTO> findAll(Pageable pageable);


    /**
     * Get the "id" amortizationUpload.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AmortizationUploadDTO> findOne(Long id);

    /**
     * Delete the "id" amortizationUpload.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the amortizationUpload corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AmortizationUploadDTO> search(String query, Pageable pageable);
}
