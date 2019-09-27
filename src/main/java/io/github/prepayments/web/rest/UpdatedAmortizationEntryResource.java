package io.github.prepayments.web.rest;

import io.github.prepayments.service.UpdatedAmortizationEntryService;
import io.github.prepayments.web.rest.errors.BadRequestAlertException;
import io.github.prepayments.service.dto.UpdatedAmortizationEntryDTO;
import io.github.prepayments.service.dto.UpdatedAmortizationEntryCriteria;
import io.github.prepayments.service.UpdatedAmortizationEntryQueryService;

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
 * REST controller for managing {@link io.github.prepayments.domain.UpdatedAmortizationEntry}.
 */
@RestController
@RequestMapping("/api")
public class UpdatedAmortizationEntryResource {

    private final Logger log = LoggerFactory.getLogger(UpdatedAmortizationEntryResource.class);

    private static final String ENTITY_NAME = "updatesUpdatedAmortizationEntry";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UpdatedAmortizationEntryService updatedAmortizationEntryService;

    private final UpdatedAmortizationEntryQueryService updatedAmortizationEntryQueryService;

    public UpdatedAmortizationEntryResource(UpdatedAmortizationEntryService updatedAmortizationEntryService, UpdatedAmortizationEntryQueryService updatedAmortizationEntryQueryService) {
        this.updatedAmortizationEntryService = updatedAmortizationEntryService;
        this.updatedAmortizationEntryQueryService = updatedAmortizationEntryQueryService;
    }

    /**
     * {@code POST  /updated-amortization-entries} : Create a new updatedAmortizationEntry.
     *
     * @param updatedAmortizationEntryDTO the updatedAmortizationEntryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new updatedAmortizationEntryDTO, or with status {@code 400 (Bad Request)} if the updatedAmortizationEntry has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/updated-amortization-entries")
    public ResponseEntity<UpdatedAmortizationEntryDTO> createUpdatedAmortizationEntry(@Valid @RequestBody UpdatedAmortizationEntryDTO updatedAmortizationEntryDTO) throws URISyntaxException {
        log.debug("REST request to save UpdatedAmortizationEntry : {}", updatedAmortizationEntryDTO);
        if (updatedAmortizationEntryDTO.getId() != null) {
            throw new BadRequestAlertException("A new updatedAmortizationEntry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UpdatedAmortizationEntryDTO result = updatedAmortizationEntryService.save(updatedAmortizationEntryDTO);
        return ResponseEntity.created(new URI("/api/updated-amortization-entries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /updated-amortization-entries} : Updates an existing updatedAmortizationEntry.
     *
     * @param updatedAmortizationEntryDTO the updatedAmortizationEntryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated updatedAmortizationEntryDTO,
     * or with status {@code 400 (Bad Request)} if the updatedAmortizationEntryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the updatedAmortizationEntryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/updated-amortization-entries")
    public ResponseEntity<UpdatedAmortizationEntryDTO> updateUpdatedAmortizationEntry(@Valid @RequestBody UpdatedAmortizationEntryDTO updatedAmortizationEntryDTO) throws URISyntaxException {
        log.debug("REST request to update UpdatedAmortizationEntry : {}", updatedAmortizationEntryDTO);
        if (updatedAmortizationEntryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UpdatedAmortizationEntryDTO result = updatedAmortizationEntryService.save(updatedAmortizationEntryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, updatedAmortizationEntryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /updated-amortization-entries} : get all the updatedAmortizationEntries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of updatedAmortizationEntries in body.
     */
    @GetMapping("/updated-amortization-entries")
    public ResponseEntity<List<UpdatedAmortizationEntryDTO>> getAllUpdatedAmortizationEntries(UpdatedAmortizationEntryCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get UpdatedAmortizationEntries by criteria: {}", criteria);
        Page<UpdatedAmortizationEntryDTO> page = updatedAmortizationEntryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /updated-amortization-entries/count} : count all the updatedAmortizationEntries.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/updated-amortization-entries/count")
    public ResponseEntity<Long> countUpdatedAmortizationEntries(UpdatedAmortizationEntryCriteria criteria) {
        log.debug("REST request to count UpdatedAmortizationEntries by criteria: {}", criteria);
        return ResponseEntity.ok().body(updatedAmortizationEntryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /updated-amortization-entries/:id} : get the "id" updatedAmortizationEntry.
     *
     * @param id the id of the updatedAmortizationEntryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updatedAmortizationEntryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/updated-amortization-entries/{id}")
    public ResponseEntity<UpdatedAmortizationEntryDTO> getUpdatedAmortizationEntry(@PathVariable Long id) {
        log.debug("REST request to get UpdatedAmortizationEntry : {}", id);
        Optional<UpdatedAmortizationEntryDTO> updatedAmortizationEntryDTO = updatedAmortizationEntryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(updatedAmortizationEntryDTO);
    }

    /**
     * {@code DELETE  /updated-amortization-entries/:id} : delete the "id" updatedAmortizationEntry.
     *
     * @param id the id of the updatedAmortizationEntryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/updated-amortization-entries/{id}")
    public ResponseEntity<Void> deleteUpdatedAmortizationEntry(@PathVariable Long id) {
        log.debug("REST request to delete UpdatedAmortizationEntry : {}", id);
        updatedAmortizationEntryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/updated-amortization-entries?query=:query} : search for the updatedAmortizationEntry corresponding
     * to the query.
     *
     * @param query the query of the updatedAmortizationEntry search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/updated-amortization-entries")
    public ResponseEntity<List<UpdatedAmortizationEntryDTO>> searchUpdatedAmortizationEntries(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of UpdatedAmortizationEntries for query {}", query);
        Page<UpdatedAmortizationEntryDTO> page = updatedAmortizationEntryService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
