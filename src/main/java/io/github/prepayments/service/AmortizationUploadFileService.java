package io.github.prepayments.service;

import io.github.prepayments.service.dto.AmortizationUploadFileDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.prepayments.domain.AmortizationUploadFile}.
 */
public interface AmortizationUploadFileService {

    /**
     * Save a amortizationUploadFile.
     *
     * @param amortizationUploadFileDTO the entity to save.
     * @return the persisted entity.
     */
    AmortizationUploadFileDTO save(AmortizationUploadFileDTO amortizationUploadFileDTO);

    /**
     * Get all the amortizationUploadFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AmortizationUploadFileDTO> findAll(Pageable pageable);


    /**
     * Get the "id" amortizationUploadFile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AmortizationUploadFileDTO> findOne(Long id);

    /**
     * Delete the "id" amortizationUploadFile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the amortizationUploadFile corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AmortizationUploadFileDTO> search(String query, Pageable pageable);
}
