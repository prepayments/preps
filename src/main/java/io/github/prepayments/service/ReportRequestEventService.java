package io.github.prepayments.service;

import io.github.prepayments.service.dto.ReportRequestEventDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.prepayments.domain.ReportRequestEvent}.
 */
public interface ReportRequestEventService {

    /**
     * Save a reportRequestEvent.
     *
     * @param reportRequestEventDTO the entity to save.
     * @return the persisted entity.
     */
    ReportRequestEventDTO save(ReportRequestEventDTO reportRequestEventDTO);

    /**
     * Get all the reportRequestEvents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ReportRequestEventDTO> findAll(Pageable pageable);


    /**
     * Get the "id" reportRequestEvent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReportRequestEventDTO> findOne(Long id);

    /**
     * Delete the "id" reportRequestEvent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the reportRequestEvent corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ReportRequestEventDTO> search(String query, Pageable pageable);
}
