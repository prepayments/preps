package io.github.prepayments.web.rest;

import io.github.prepayments.service.RegisteredSupplierService;
import io.github.prepayments.web.rest.errors.BadRequestAlertException;
import io.github.prepayments.service.dto.RegisteredSupplierDTO;
import io.github.prepayments.service.dto.RegisteredSupplierCriteria;
import io.github.prepayments.service.RegisteredSupplierQueryService;

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
 * REST controller for managing {@link io.github.prepayments.domain.RegisteredSupplier}.
 */
@RestController
@RequestMapping("/api")
public class RegisteredSupplierResource {

    private final Logger log = LoggerFactory.getLogger(RegisteredSupplierResource.class);

    private static final String ENTITY_NAME = "prepaymentsRegisteredSupplier";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegisteredSupplierService registeredSupplierService;

    private final RegisteredSupplierQueryService registeredSupplierQueryService;

    public RegisteredSupplierResource(RegisteredSupplierService registeredSupplierService, RegisteredSupplierQueryService registeredSupplierQueryService) {
        this.registeredSupplierService = registeredSupplierService;
        this.registeredSupplierQueryService = registeredSupplierQueryService;
    }

    /**
     * {@code POST  /registered-suppliers} : Create a new registeredSupplier.
     *
     * @param registeredSupplierDTO the registeredSupplierDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new registeredSupplierDTO, or with status {@code 400 (Bad Request)} if the registeredSupplier has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/registered-suppliers")
    public ResponseEntity<RegisteredSupplierDTO> createRegisteredSupplier(@Valid @RequestBody RegisteredSupplierDTO registeredSupplierDTO) throws URISyntaxException {
        log.debug("REST request to save RegisteredSupplier : {}", registeredSupplierDTO);
        if (registeredSupplierDTO.getId() != null) {
            throw new BadRequestAlertException("A new registeredSupplier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RegisteredSupplierDTO result = registeredSupplierService.save(registeredSupplierDTO);
        return ResponseEntity.created(new URI("/api/registered-suppliers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /registered-suppliers} : Updates an existing registeredSupplier.
     *
     * @param registeredSupplierDTO the registeredSupplierDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated registeredSupplierDTO,
     * or with status {@code 400 (Bad Request)} if the registeredSupplierDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the registeredSupplierDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/registered-suppliers")
    public ResponseEntity<RegisteredSupplierDTO> updateRegisteredSupplier(@Valid @RequestBody RegisteredSupplierDTO registeredSupplierDTO) throws URISyntaxException {
        log.debug("REST request to update RegisteredSupplier : {}", registeredSupplierDTO);
        if (registeredSupplierDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RegisteredSupplierDTO result = registeredSupplierService.save(registeredSupplierDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, registeredSupplierDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /registered-suppliers} : get all the registeredSuppliers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of registeredSuppliers in body.
     */
    @GetMapping("/registered-suppliers")
    public ResponseEntity<List<RegisteredSupplierDTO>> getAllRegisteredSuppliers(RegisteredSupplierCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get RegisteredSuppliers by criteria: {}", criteria);
        Page<RegisteredSupplierDTO> page = registeredSupplierQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /registered-suppliers/count} : count all the registeredSuppliers.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/registered-suppliers/count")
    public ResponseEntity<Long> countRegisteredSuppliers(RegisteredSupplierCriteria criteria) {
        log.debug("REST request to count RegisteredSuppliers by criteria: {}", criteria);
        return ResponseEntity.ok().body(registeredSupplierQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /registered-suppliers/:id} : get the "id" registeredSupplier.
     *
     * @param id the id of the registeredSupplierDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the registeredSupplierDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/registered-suppliers/{id}")
    public ResponseEntity<RegisteredSupplierDTO> getRegisteredSupplier(@PathVariable Long id) {
        log.debug("REST request to get RegisteredSupplier : {}", id);
        Optional<RegisteredSupplierDTO> registeredSupplierDTO = registeredSupplierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(registeredSupplierDTO);
    }

    /**
     * {@code DELETE  /registered-suppliers/:id} : delete the "id" registeredSupplier.
     *
     * @param id the id of the registeredSupplierDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/registered-suppliers/{id}")
    public ResponseEntity<Void> deleteRegisteredSupplier(@PathVariable Long id) {
        log.debug("REST request to delete RegisteredSupplier : {}", id);
        registeredSupplierService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/registered-suppliers?query=:query} : search for the registeredSupplier corresponding
     * to the query.
     *
     * @param query the query of the registeredSupplier search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/registered-suppliers")
    public ResponseEntity<List<RegisteredSupplierDTO>> searchRegisteredSuppliers(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of RegisteredSuppliers for query {}", query);
        Page<RegisteredSupplierDTO> page = registeredSupplierService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
