package io.github.prepayments.web.rest;

import io.github.prepayments.service.ReportTypeService;
import io.github.prepayments.web.rest.errors.BadRequestAlertException;
import io.github.prepayments.service.dto.ReportTypeDTO;
import io.github.prepayments.service.dto.ReportTypeCriteria;
import io.github.prepayments.service.ReportTypeQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.github.prepayments.domain.ReportType}.
 */
@RestController
@RequestMapping("/api")
public class ReportTypeResource {

    private final Logger log = LoggerFactory.getLogger(ReportTypeResource.class);

    private static final String ENTITY_NAME = "reportsReportType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReportTypeService reportTypeService;

    private final ReportTypeQueryService reportTypeQueryService;

    public ReportTypeResource(ReportTypeService reportTypeService, ReportTypeQueryService reportTypeQueryService) {
        this.reportTypeService = reportTypeService;
        this.reportTypeQueryService = reportTypeQueryService;
    }

    /**
     * {@code POST  /report-types} : Create a new reportType.
     *
     * @param reportTypeDTO the reportTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reportTypeDTO, or with status {@code 400 (Bad Request)} if the reportType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/report-types")
    public ResponseEntity<ReportTypeDTO> createReportType(@RequestBody ReportTypeDTO reportTypeDTO) throws URISyntaxException {
        log.debug("REST request to save ReportType : {}", reportTypeDTO);
        if (reportTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new reportType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReportTypeDTO result = reportTypeService.save(reportTypeDTO);
        return ResponseEntity.created(new URI("/api/report-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /report-types} : Updates an existing reportType.
     *
     * @param reportTypeDTO the reportTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reportTypeDTO,
     * or with status {@code 400 (Bad Request)} if the reportTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reportTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/report-types")
    public ResponseEntity<ReportTypeDTO> updateReportType(@RequestBody ReportTypeDTO reportTypeDTO) throws URISyntaxException {
        log.debug("REST request to update ReportType : {}", reportTypeDTO);
        if (reportTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReportTypeDTO result = reportTypeService.save(reportTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, reportTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /report-types} : get all the reportTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reportTypes in body.
     */
    @GetMapping("/report-types")
    public ResponseEntity<List<ReportTypeDTO>> getAllReportTypes(ReportTypeCriteria criteria) {
        log.debug("REST request to get ReportTypes by criteria: {}", criteria);
        List<ReportTypeDTO> entityList = reportTypeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /report-types/count} : count all the reportTypes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/report-types/count")
    public ResponseEntity<Long> countReportTypes(ReportTypeCriteria criteria) {
        log.debug("REST request to count ReportTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(reportTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /report-types/:id} : get the "id" reportType.
     *
     * @param id the id of the reportTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reportTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/report-types/{id}")
    public ResponseEntity<ReportTypeDTO> getReportType(@PathVariable Long id) {
        log.debug("REST request to get ReportType : {}", id);
        Optional<ReportTypeDTO> reportTypeDTO = reportTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reportTypeDTO);
    }

    /**
     * {@code DELETE  /report-types/:id} : delete the "id" reportType.
     *
     * @param id the id of the reportTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/report-types/{id}")
    public ResponseEntity<Void> deleteReportType(@PathVariable Long id) {
        log.debug("REST request to delete ReportType : {}", id);
        reportTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
