package io.github.prepayments.service;

import io.github.prepayments.service.dto.RegisteredSupplierDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.prepayments.domain.RegisteredSupplier}.
 */
public interface RegisteredSupplierService {

    /**
     * Save a registeredSupplier.
     *
     * @param registeredSupplierDTO the entity to save.
     * @return the persisted entity.
     */
    RegisteredSupplierDTO save(RegisteredSupplierDTO registeredSupplierDTO);

    /**
     * Get all the registeredSuppliers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RegisteredSupplierDTO> findAll(Pageable pageable);


    /**
     * Get the "id" registeredSupplier.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RegisteredSupplierDTO> findOne(Long id);

    /**
     * Delete the "id" registeredSupplier.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the registeredSupplier corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RegisteredSupplierDTO> search(String query, Pageable pageable);
}
