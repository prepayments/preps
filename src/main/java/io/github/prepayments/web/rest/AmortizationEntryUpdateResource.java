package io.github.prepayments.web.rest;

import io.github.prepayments.service.AmortizationEntryUpdateService;
import io.github.prepayments.web.rest.errors.BadRequestAlertException;
import io.github.prepayments.service.dto.AmortizationEntryUpdateDTO;
import io.github.prepayments.service.dto.AmortizationEntryUpdateCriteria;
import io.github.prepayments.service.AmortizationEntryUpdateQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link io.github.prepayments.domain.AmortizationEntryUpdate}.
 */
@RestController
@RequestMapping("/api")
public class AmortizationEntryUpdateResource {

    private final Logger log = LoggerFactory.getLogger(AmortizationEntryUpdateResource.class);

    private static final String ENTITY_NAME = "updatesAmortizationEntryUpdate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AmortizationEntryUpdateService amortizationEntryUpdateService;

    private final AmortizationEntryUpdateQueryService amortizationEntryUpdateQueryService;

    public AmortizationEntryUpdateResource(AmortizationEntryUpdateService amortizationEntryUpdateService, AmortizationEntryUpdateQueryService amortizationEntryUpdateQueryService) {
        this.amortizationEntryUpdateService = amortizationEntryUpdateService;
        this.amortizationEntryUpdateQueryService = amortizationEntryUpdateQueryService;
    }

    /**
     * {@code POST  /amortization-entry-updates} : Create a new amortizationEntryUpdate.
     *
     * @param amortizationEntryUpdateDTO the amortizationEntryUpdateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new amortizationEntryUpdateDTO, or with status {@code 400 (Bad Request)} if the amortizationEntryUpdate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/amortization-entry-updates")
    public ResponseEntity<AmortizationEntryUpdateDTO> createAmortizationEntryUpdate(@Valid @RequestBody AmortizationEntryUpdateDTO amortizationEntryUpdateDTO) throws URISyntaxException {
        log.debug("REST request to save AmortizationEntryUpdate : {}", amortizationEntryUpdateDTO);
        if (amortizationEntryUpdateDTO.getId() != null) {
            throw new BadRequestAlertException("A new amortizationEntryUpdate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AmortizationEntryUpdateDTO result = amortizationEntryUpdateService.save(amortizationEntryUpdateDTO);
        return ResponseEntity.created(new URI("/api/amortization-entry-updates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /amortization-entry-updates} : Updates an existing amortizationEntryUpdate.
     *
     * @param amortizationEntryUpdateDTO the amortizationEntryUpdateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated amortizationEntryUpdateDTO,
     * or with status {@code 400 (Bad Request)} if the amortizationEntryUpdateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the amortizationEntryUpdateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/amortization-entry-updates")
    public ResponseEntity<AmortizationEntryUpdateDTO> updateAmortizationEntryUpdate(@Valid @RequestBody AmortizationEntryUpdateDTO amortizationEntryUpdateDTO) throws URISyntaxException {
        log.debug("REST request to update AmortizationEntryUpdate : {}", amortizationEntryUpdateDTO);
        if (amortizationEntryUpdateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AmortizationEntryUpdateDTO result = amortizationEntryUpdateService.save(amortizationEntryUpdateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, amortizationEntryUpdateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /amortization-entry-updates} : get all the amortizationEntryUpdates.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of amortizationEntryUpdates in body.
     */
    @GetMapping("/amortization-entry-updates")
    public ResponseEntity<List<AmortizationEntryUpdateDTO>> getAllAmortizationEntryUpdates(AmortizationEntryUpdateCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get AmortizationEntryUpdates by criteria: {}", criteria);
        Page<AmortizationEntryUpdateDTO> page = amortizationEntryUpdateQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /amortization-entry-updates/count} : count all the amortizationEntryUpdates.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/amortization-entry-updates/count")
    public ResponseEntity<Long> countAmortizationEntryUpdates(AmortizationEntryUpdateCriteria criteria) {
        log.debug("REST request to count AmortizationEntryUpdates by criteria: {}", criteria);
        return ResponseEntity.ok().body(amortizationEntryUpdateQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /amortization-entry-updates/:id} : get the "id" amortizationEntryUpdate.
     *
     * @param id the id of the amortizationEntryUpdateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the amortizationEntryUpdateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/amortization-entry-updates/{id}")
    public ResponseEntity<AmortizationEntryUpdateDTO> getAmortizationEntryUpdate(@PathVariable Long id) {
        log.debug("REST request to get AmortizationEntryUpdate : {}", id);
        Optional<AmortizationEntryUpdateDTO> amortizationEntryUpdateDTO = amortizationEntryUpdateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(amortizationEntryUpdateDTO);
    }

    /**
     * {@code DELETE  /amortization-entry-updates/:id} : delete the "id" amortizationEntryUpdate.
     *
     * @param id the id of the amortizationEntryUpdateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/amortization-entry-updates/{id}")
    public ResponseEntity<Void> deleteAmortizationEntryUpdate(@PathVariable Long id) {
        log.debug("REST request to delete AmortizationEntryUpdate : {}", id);
        amortizationEntryUpdateService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/amortization-entry-updates?query=:query} : search for the amortizationEntryUpdate corresponding
     * to the query.
     *
     * @param query the query of the amortizationEntryUpdate search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/amortization-entry-updates")
    public ResponseEntity<List<AmortizationEntryUpdateDTO>> searchAmortizationEntryUpdates(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of AmortizationEntryUpdates for query {}", query);
        Page<AmortizationEntryUpdateDTO> page = amortizationEntryUpdateService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
