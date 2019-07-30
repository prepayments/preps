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

    /**
     * Search for the supplierDataEntryFile corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SupplierDataEntryFileDTO> search(String query, Pageable pageable);
}
