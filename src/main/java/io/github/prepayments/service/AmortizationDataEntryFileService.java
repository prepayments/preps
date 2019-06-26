package io.github.prepayments.service;

import io.github.prepayments.service.dto.AmortizationDataEntryFileDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.prepayments.domain.AmortizationDataEntryFile}.
 */
public interface AmortizationDataEntryFileService {

    /**
     * Save a amortizationDataEntryFile.
     *
     * @param amortizationDataEntryFileDTO the entity to save.
     * @return the persisted entity.
     */
    AmortizationDataEntryFileDTO save(AmortizationDataEntryFileDTO amortizationDataEntryFileDTO);

    /**
     * Get all the amortizationDataEntryFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AmortizationDataEntryFileDTO> findAll(Pageable pageable);


    /**
     * Get the "id" amortizationDataEntryFile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AmortizationDataEntryFileDTO> findOne(Long id);

    /**
     * Delete the "id" amortizationDataEntryFile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
