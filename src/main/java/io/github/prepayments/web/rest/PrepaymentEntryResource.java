package io.github.prepayments.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.github.prepayments.service.PrepaymentEntryQueryService;
import io.github.prepayments.service.PrepaymentEntryService;
import io.github.prepayments.service.dto.PrepaymentEntryCriteria;
import io.github.prepayments.service.dto.PrepaymentEntryDTO;
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
 * REST controller for managing {@link io.github.prepayments.domain.PrepaymentEntry}.
 */
@RestController
@RequestMapping("/api")
public class PrepaymentEntryResource {

    private final Logger log = LoggerFactory.getLogger(PrepaymentEntryResource.class);

    private static final String ENTITY_NAME = "prepaymentsPrepaymentEntry";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrepaymentEntryService prepaymentEntryService;

    private final PrepaymentEntryQueryService prepaymentEntryQueryService;

    public PrepaymentEntryResource(PrepaymentEntryService prepaymentEntryService, PrepaymentEntryQueryService prepaymentEntryQueryService) {
        this.prepaymentEntryService = prepaymentEntryService;
        this.prepaymentEntryQueryService = prepaymentEntryQueryService;
    }

    /**
     * {@code POST  /prepayment-entries} : Create a new prepaymentEntry.
     *
     * @param prepaymentEntryDTO the prepaymentEntryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prepaymentEntryDTO, or with status {@code 400 (Bad Request)} if the prepaymentEntry has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prepayment-entries")
    public ResponseEntity<PrepaymentEntryDTO> createPrepaymentEntry(@Valid @RequestBody PrepaymentEntryDTO prepaymentEntryDTO) throws URISyntaxException {
        log.debug("REST request to save PrepaymentEntry : {}", prepaymentEntryDTO);
        if (prepaymentEntryDTO.getId() != null) {
            throw new BadRequestAlertException("A new prepaymentEntry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrepaymentEntryDTO result = prepaymentEntryService.save(prepaymentEntryDTO);
        return ResponseEntity.created(new URI("/api/prepayment-entries/" + result.getId()))
                             .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                             .body(result);
    }

    /**
     * {@code PUT  /prepayment-entries} : Updates an existing prepaymentEntry.
     *
     * @param prepaymentEntryDTO the prepaymentEntryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prepaymentEntryDTO,
     * or with status {@code 400 (Bad Request)} if the prepaymentEntryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prepaymentEntryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prepayment-entries")
    public ResponseEntity<PrepaymentEntryDTO> updatePrepaymentEntry(@Valid @RequestBody PrepaymentEntryDTO prepaymentEntryDTO) throws URISyntaxException {
        log.debug("REST request to update PrepaymentEntry : {}", prepaymentEntryDTO);
        if (prepaymentEntryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PrepaymentEntryDTO result = prepaymentEntryService.save(prepaymentEntryDTO);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, prepaymentEntryDTO.getId().toString())).body(result);
    }

    /**
     * {@code GET  /prepayment-entries} : get all the prepaymentEntries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prepaymentEntries in body.
     */
    @GetMapping("/prepayment-entries")
    public ResponseEntity<List<PrepaymentEntryDTO>> getAllPrepaymentEntries(PrepaymentEntryCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams,
                                                                            UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get PrepaymentEntries by criteria: {}", criteria);
        Page<PrepaymentEntryDTO> page = prepaymentEntryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /prepayment-entries/count} : count all the prepaymentEntries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/prepayment-entries/count")
    public ResponseEntity<Long> countPrepaymentEntries(PrepaymentEntryCriteria criteria) {
        log.debug("REST request to count PrepaymentEntries by criteria: {}", criteria);
        return ResponseEntity.ok().body(prepaymentEntryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /prepayment-entries/:id} : get the "id" prepaymentEntry.
     *
     * @param id the id of the prepaymentEntryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prepaymentEntryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prepayment-entries/{id}")
    public ResponseEntity<PrepaymentEntryDTO> getPrepaymentEntry(@PathVariable Long id) {
        log.debug("REST request to get PrepaymentEntry : {}", id);
        Optional<PrepaymentEntryDTO> prepaymentEntryDTO = prepaymentEntryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prepaymentEntryDTO);
    }

    /**
     * {@code DELETE  /prepayment-entries/:id} : delete the "id" prepaymentEntry.
     *
     * @param id the id of the prepaymentEntryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prepayment-entries/{id}")
    public ResponseEntity<Void> deletePrepaymentEntry(@PathVariable Long id) {
        log.debug("REST request to delete PrepaymentEntry : {}", id);
        prepaymentEntryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/prepayment-entries?query=:query} : search for the prepaymentEntry corresponding
     * to the query.
     *
     * @param query the query of the prepaymentEntry search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/prepayment-entries")
    public ResponseEntity<List<PrepaymentEntryDTO>> searchPrepaymentEntries(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams,
                                                                            UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of PrepaymentEntries for query {}", query);
        Page<PrepaymentEntryDTO> page = prepaymentEntryService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
