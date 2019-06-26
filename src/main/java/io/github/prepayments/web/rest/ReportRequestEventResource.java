package io.github.prepayments.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.github.prepayments.service.ReportRequestEventQueryService;
import io.github.prepayments.service.ReportRequestEventService;
import io.github.prepayments.service.dto.ReportRequestEventCriteria;
import io.github.prepayments.service.dto.ReportRequestEventDTO;
import io.github.prepayments.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.github.prepayments.domain.ReportRequestEvent}.
 */
@RestController
@RequestMapping("/api")
public class ReportRequestEventResource {

    private static final String ENTITY_NAME = "reportsReportRequestEvent";
    private final Logger log = LoggerFactory.getLogger(ReportRequestEventResource.class);
    private final ReportRequestEventService reportRequestEventService;
    private final ReportRequestEventQueryService reportRequestEventQueryService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public ReportRequestEventResource(ReportRequestEventService reportRequestEventService, ReportRequestEventQueryService reportRequestEventQueryService) {
        this.reportRequestEventService = reportRequestEventService;
        this.reportRequestEventQueryService = reportRequestEventQueryService;
    }

    /**
     * {@code POST  /report-request-events} : Create a new reportRequestEvent.
     *
     * @param reportRequestEventDTO the reportRequestEventDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reportRequestEventDTO, or with status {@code 400 (Bad Request)} if the reportRequestEvent has already
     * an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/report-request-events")
    public ResponseEntity<ReportRequestEventDTO> createReportRequestEvent(@RequestBody ReportRequestEventDTO reportRequestEventDTO) throws URISyntaxException {
        log.debug("REST request to save ReportRequestEvent : {}", reportRequestEventDTO);
        if (reportRequestEventDTO.getId() != null) {
            throw new BadRequestAlertException("A new reportRequestEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReportRequestEventDTO result = reportRequestEventService.save(reportRequestEventDTO);
        return ResponseEntity.created(new URI("/api/report-request-events/" + result.getId()))
                             .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                             .body(result);
    }

    /**
     * {@code PUT  /report-request-events} : Updates an existing reportRequestEvent.
     *
     * @param reportRequestEventDTO the reportRequestEventDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reportRequestEventDTO, or with status {@code 400 (Bad Request)} if the reportRequestEventDTO is not
     * valid, or with status {@code 500 (Internal Server Error)} if the reportRequestEventDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/report-request-events")
    public ResponseEntity<ReportRequestEventDTO> updateReportRequestEvent(@RequestBody ReportRequestEventDTO reportRequestEventDTO) throws URISyntaxException {
        log.debug("REST request to update ReportRequestEvent : {}", reportRequestEventDTO);
        if (reportRequestEventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReportRequestEventDTO result = reportRequestEventService.save(reportRequestEventDTO);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, reportRequestEventDTO.getId().toString())).body(result);
    }

    /**
     * {@code GET  /report-request-events} : get all the reportRequestEvents.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reportRequestEvents in body.
     */
    @GetMapping("/report-request-events")
    public ResponseEntity<List<ReportRequestEventDTO>> getAllReportRequestEvents(ReportRequestEventCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams,
                                                                                 UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get ReportRequestEvents by criteria: {}", criteria);
        Page<ReportRequestEventDTO> page = reportRequestEventQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /report-request-events/count} : count all the reportRequestEvents.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/report-request-events/count")
    public ResponseEntity<Long> countReportRequestEvents(ReportRequestEventCriteria criteria) {
        log.debug("REST request to count ReportRequestEvents by criteria: {}", criteria);
        return ResponseEntity.ok().body(reportRequestEventQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /report-request-events/:id} : get the "id" reportRequestEvent.
     *
     * @param id the id of the reportRequestEventDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reportRequestEventDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/report-request-events/{id}")
    public ResponseEntity<ReportRequestEventDTO> getReportRequestEvent(@PathVariable Long id) {
        log.debug("REST request to get ReportRequestEvent : {}", id);
        Optional<ReportRequestEventDTO> reportRequestEventDTO = reportRequestEventService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reportRequestEventDTO);
    }

    /**
     * {@code DELETE  /report-request-events/:id} : delete the "id" reportRequestEvent.
     *
     * @param id the id of the reportRequestEventDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/report-request-events/{id}")
    public ResponseEntity<Void> deleteReportRequestEvent(@PathVariable Long id) {
        log.debug("REST request to delete ReportRequestEvent : {}", id);
        reportRequestEventService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/report-request-events?query=:query} : search for the reportRequestEvent corresponding to the query.
     *
     * @param query    the query of the reportRequestEvent search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/report-request-events")
    public ResponseEntity<List<ReportRequestEventDTO>> searchReportRequestEvents(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams,
                                                                                 UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of ReportRequestEvents for query {}", query);
        Page<ReportRequestEventDTO> page = reportRequestEventService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
