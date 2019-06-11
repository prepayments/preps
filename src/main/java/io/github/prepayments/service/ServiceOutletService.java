package io.github.prepayments.service;

import io.github.prepayments.service.dto.ServiceOutletDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.prepayments.domain.ServiceOutlet}.
 */
public interface ServiceOutletService {

    /**
     * Save a serviceOutlet.
     *
     * @param serviceOutletDTO the entity to save.
     * @return the persisted entity.
     */
    ServiceOutletDTO save(ServiceOutletDTO serviceOutletDTO);

    /**
     * Get all the serviceOutlets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ServiceOutletDTO> findAll(Pageable pageable);


    /**
     * Get the "id" serviceOutlet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceOutletDTO> findOne(Long id);

    /**
     * Delete the "id" serviceOutlet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
