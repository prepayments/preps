package io.github.prepayments.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.github.prepayments.app.messaging.data_entry.service.AmortizationEntriesPropagatorService;
import io.github.prepayments.service.AmortizationUploadQueryService;
import io.github.prepayments.service.AmortizationUploadService;
import io.github.prepayments.service.dto.AmortizationUploadCriteria;
import io.github.prepayments.service.dto.AmortizationUploadDTO;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.github.prepayments.domain.AmortizationUpload}.
 */
@RestController
@RequestMapping("/api")
public class AmortizationUploadResource {

    private static final String ENTITY_NAME = "dataEntryAmortizationUpload";
    private final Logger log = LoggerFactory.getLogger(AmortizationUploadResource.class);
    private final AmortizationUploadService amortizationUploadService;
    private final AmortizationUploadQueryService amortizationUploadQueryService;
    private final AmortizationEntriesPropagatorService amortizationEntriesPropagatorService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public AmortizationUploadResource(AmortizationUploadService amortizationUploadService, AmortizationUploadQueryService amortizationUploadQueryService,
                                      final AmortizationEntriesPropagatorService amortizationEntriesPropagatorService) {
        this.amortizationUploadService = amortizationUploadService;
        this.amortizationUploadQueryService = amortizationUploadQueryService;
        this.amortizationEntriesPropagatorService = amortizationEntriesPropagatorService;
    }

    /**
     * {@code POST  /amortization-uploads} : Create a new amortizationUpload.
     *
     * @param amortizationUploadDTO the amortizationUploadDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new amortizationUploadDTO, or with status {@code 400 (Bad Request)} if the amortizationUpload has already
     * an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/amortization-uploads")
    public ResponseEntity<AmortizationUploadDTO> createAmortizationUpload(@Valid @RequestBody AmortizationUploadDTO amortizationUploadDTO) throws URISyntaxException {
        log.debug("REST request to save AmortizationUpload : {}", amortizationUploadDTO);
        if (amortizationUploadDTO.getId() != null) {
            throw new BadRequestAlertException("A new amortizationUpload cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AmortizationUploadDTO result = amortizationUploadService.save(amortizationUploadDTO);
        amortizationEntriesPropagatorService.propagateAmortizationEntries(result);
        return ResponseEntity.created(new URI("/api/amortization-uploads/" + result.getId()))
                             .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                             .body(result);
    }

    /**
     * {@code PUT  /amortization-uploads} : Updates an existing amortizationUpload.
     *
     * @param amortizationUploadDTO the amortizationUploadDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated amortizationUploadDTO, or with status {@code 400 (Bad Request)} if the amortizationUploadDTO is not
     * valid, or with status {@code 500 (Internal Server Error)} if the amortizationUploadDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/amortization-uploads")
    public ResponseEntity<AmortizationUploadDTO> updateAmortizationUpload(@Valid @RequestBody AmortizationUploadDTO amortizationUploadDTO) throws URISyntaxException {
        log.debug("REST request to update AmortizationUpload : {}", amortizationUploadDTO);
        if (amortizationUploadDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AmortizationUploadDTO result = amortizationUploadService.save(amortizationUploadDTO);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, amortizationUploadDTO.getId().toString())).body(result);
    }

    /**
     * {@code GET  /amortization-uploads} : get all the amortizationUploads.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of amortizationUploads in body.
     */
    @GetMapping("/amortization-uploads")
    public ResponseEntity<List<AmortizationUploadDTO>> getAllAmortizationUploads(AmortizationUploadCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams,
                                                                                 UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get AmortizationUploads by criteria: {}", criteria);
        Page<AmortizationUploadDTO> page = amortizationUploadQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /amortization-uploads/count} : count all the amortizationUploads.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/amortization-uploads/count")
    public ResponseEntity<Long> countAmortizationUploads(AmortizationUploadCriteria criteria) {
        log.debug("REST request to count AmortizationUploads by criteria: {}", criteria);
        return ResponseEntity.ok().body(amortizationUploadQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /amortization-uploads/:id} : get the "id" amortizationUpload.
     *
     * @param id the id of the amortizationUploadDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the amortizationUploadDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/amortization-uploads/{id}")
    public ResponseEntity<AmortizationUploadDTO> getAmortizationUpload(@PathVariable Long id) {
        log.debug("REST request to get AmortizationUpload : {}", id);
        Optional<AmortizationUploadDTO> amortizationUploadDTO = amortizationUploadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(amortizationUploadDTO);
    }

    /**
     * {@code DELETE  /amortization-uploads/:id} : delete the "id" amortizationUpload.
     *
     * @param id the id of the amortizationUploadDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/amortization-uploads/{id}")
    public ResponseEntity<Void> deleteAmortizationUpload(@PathVariable Long id) {
        log.debug("REST request to delete AmortizationUpload : {}", id);
        amortizationUploadService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/amortization-uploads?query=:query} : search for the amortizationUpload corresponding to the query.
     *
     * @param query    the query of the amortizationUpload search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/amortization-uploads")
    public ResponseEntity<List<AmortizationUploadDTO>> searchAmortizationUploads(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams,
                                                                                 UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of AmortizationUploads for query {}", query);
        Page<AmortizationUploadDTO> page = amortizationUploadService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
