package io.github.prepayments.service;

import io.github.prepayments.service.dto.ServiceOutletDataEntryFileDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.prepayments.domain.ServiceOutletDataEntryFile}.
 */
public interface ServiceOutletDataEntryFileService {

    /**
     * Save a serviceOutletDataEntryFile.
     *
     * @param serviceOutletDataEntryFileDTO the entity to save.
     * @return the persisted entity.
     */
    ServiceOutletDataEntryFileDTO save(ServiceOutletDataEntryFileDTO serviceOutletDataEntryFileDTO);

    /**
     * Get all the serviceOutletDataEntryFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ServiceOutletDataEntryFileDTO> findAll(Pageable pageable);


    /**
     * Get the "id" serviceOutletDataEntryFile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceOutletDataEntryFileDTO> findOne(Long id);

    /**
     * Delete the "id" serviceOutletDataEntryFile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
