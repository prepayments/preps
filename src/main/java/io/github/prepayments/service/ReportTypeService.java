package io.github.prepayments.service;

import io.github.prepayments.service.dto.ReportTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.prepayments.domain.ReportType}.
 */
public interface ReportTypeService {

    /**
     * Save a reportType.
     *
     * @param reportTypeDTO the entity to save.
     * @return the persisted entity.
     */
    ReportTypeDTO save(ReportTypeDTO reportTypeDTO);

    /**
     * Get all the reportTypes.
     *
     * @return the list of entities.
     */
    List<ReportTypeDTO> findAll();


    /**
     * Get the "id" reportType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReportTypeDTO> findOne(Long id);

    /**
     * Delete the "id" reportType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
