package io.github.prepayments.service;

import io.github.prepayments.service.dto.PrepaymentDataEntryFileDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.prepayments.domain.PrepaymentDataEntryFile}.
 */
public interface PrepaymentDataEntryFileService {

    /**
     * Save a prepaymentDataEntryFile.
     *
     * @param prepaymentDataEntryFileDTO the entity to save.
     * @return the persisted entity.
     */
    PrepaymentDataEntryFileDTO save(PrepaymentDataEntryFileDTO prepaymentDataEntryFileDTO);

    /**
     * Get all the prepaymentDataEntryFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PrepaymentDataEntryFileDTO> findAll(Pageable pageable);


    /**
     * Get the "id" prepaymentDataEntryFile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PrepaymentDataEntryFileDTO> findOne(Long id);

    /**
     * Delete the "id" prepaymentDataEntryFile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
