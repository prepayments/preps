package io.github.prepayments.service;

import io.github.prepayments.service.dto.SupplierDataEntryFileDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.prepayments.domain.SupplierDataEntryFile}.
 */
public interface SupplierDataEntryFileService {

    /**
     * Save a supplierDataEntryFile.
     *
     * @param supplierDataEntryFileDTO the entity to save.
     * @return the persisted entity.
     */
    SupplierDataEntryFileDTO save(SupplierDataEntryFileDTO supplierDataEntryFileDTO);

    /**
     * Get all the supplierDataEntryFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SupplierDataEntryFileDTO> findAll(Pageable pageable);


    /**
     * Get the "id" supplierDataEntryFile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SupplierDataEntryFileDTO> findOne(Long id);

    /**
     * Delete the "id" supplierDataEntryFile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
