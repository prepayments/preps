package io.github.prepayments.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.github.prepayments.app.decoratedResource.IPrepaymentDataEntryFileResource;
import io.github.prepayments.service.PrepaymentDataEntryFileQueryService;
import io.github.prepayments.service.PrepaymentDataEntryFileService;
import io.github.prepayments.service.dto.PrepaymentDataEntryFileCriteria;
import io.github.prepayments.service.dto.PrepaymentDataEntryFileDTO;
import io.github.prepayments.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.github.prepayments.domain.PrepaymentDataEntryFile}.
 */
@Component("prepaymentDataEntryFileResourceDelegate")
public class PrepaymentDataEntryFileResource implements IPrepaymentDataEntryFileResource {

    private static final String ENTITY_NAME = "dataEntryPrepaymentDataEntryFile";
    private final Logger log = LoggerFactory.getLogger(PrepaymentDataEntryFileResource.class);
    private final PrepaymentDataEntryFileService prepaymentDataEntryFileService;
    private final PrepaymentDataEntryFileQueryService prepaymentDataEntryFileQueryService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public PrepaymentDataEntryFileResource(PrepaymentDataEntryFileService prepaymentDataEntryFileService, PrepaymentDataEntryFileQueryService prepaymentDataEntryFileQueryService) {
        this.prepaymentDataEntryFileService = prepaymentDataEntryFileService;
        this.prepaymentDataEntryFileQueryService = prepaymentDataEntryFileQueryService;
    }

    /**
     * {@code POST  /prepayment-data-entry-files} : Create a new prepaymentDataEntryFile.
     *
     * @param prepaymentDataEntryFileDTO the prepaymentDataEntryFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prepaymentDataEntryFileDTO, or with status {@code 400 (Bad Request)} if the prepaymentDataEntryFile
     * has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Override
    @PostMapping("/prepayment-data-entry-files")
    public ResponseEntity<PrepaymentDataEntryFileDTO> createPrepaymentDataEntryFile(@Valid @RequestBody PrepaymentDataEntryFileDTO prepaymentDataEntryFileDTO) throws URISyntaxException {
        log.debug("REST request to save PrepaymentDataEntryFile : {}", prepaymentDataEntryFileDTO);
        if (prepaymentDataEntryFileDTO.getId() != null) {
            throw new BadRequestAlertException("A new prepaymentDataEntryFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrepaymentDataEntryFileDTO result = prepaymentDataEntryFileService.save(prepaymentDataEntryFileDTO);
        return ResponseEntity.created(new URI("/api/prepayment-data-entry-files/" + result.getId()))
                             .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                             .body(result);
    }

    /**
     * {@code PUT  /prepayment-data-entry-files} : Updates an existing prepaymentDataEntryFile.
     *
     * @param prepaymentDataEntryFileDTO the prepaymentDataEntryFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prepaymentDataEntryFileDTO, or with status {@code 400 (Bad Request)} if the prepaymentDataEntryFileDTO
     * is not valid, or with status {@code 500 (Internal Server Error)} if the prepaymentDataEntryFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Override
    @PutMapping("/prepayment-data-entry-files")
    public ResponseEntity<PrepaymentDataEntryFileDTO> updatePrepaymentDataEntryFile(@Valid @RequestBody PrepaymentDataEntryFileDTO prepaymentDataEntryFileDTO) throws URISyntaxException {
        log.debug("REST request to update PrepaymentDataEntryFile : {}", prepaymentDataEntryFileDTO);
        if (prepaymentDataEntryFileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PrepaymentDataEntryFileDTO result = prepaymentDataEntryFileService.save(prepaymentDataEntryFileDTO);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, prepaymentDataEntryFileDTO.getId().toString())).body(result);
    }

    /**
     * {@code GET  /prepayment-data-entry-files} : get all the prepaymentDataEntryFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prepaymentDataEntryFiles in body.
     */
    @Override
    @GetMapping("/prepayment-data-entry-files")
    public ResponseEntity<List<PrepaymentDataEntryFileDTO>> getAllPrepaymentDataEntryFiles(PrepaymentDataEntryFileCriteria criteria, Pageable pageable,
                                                                                           @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get PrepaymentDataEntryFiles by criteria: {}", criteria);
        Page<PrepaymentDataEntryFileDTO> page = prepaymentDataEntryFileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /prepayment-data-entry-files/count} : count all the prepaymentDataEntryFiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @Override
    @GetMapping("/prepayment-data-entry-files/count")
    public ResponseEntity<Long> countPrepaymentDataEntryFiles(PrepaymentDataEntryFileCriteria criteria) {
        log.debug("REST request to count PrepaymentDataEntryFiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(prepaymentDataEntryFileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /prepayment-data-entry-files/:id} : get the "id" prepaymentDataEntryFile.
     *
     * @param id the id of the prepaymentDataEntryFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prepaymentDataEntryFileDTO, or with status {@code 404 (Not Found)}.
     */
    @Override
    @GetMapping("/prepayment-data-entry-files/{id}")
    public ResponseEntity<PrepaymentDataEntryFileDTO> getPrepaymentDataEntryFile(@PathVariable Long id) {
        log.debug("REST request to get PrepaymentDataEntryFile : {}", id);
        Optional<PrepaymentDataEntryFileDTO> prepaymentDataEntryFileDTO = prepaymentDataEntryFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prepaymentDataEntryFileDTO);
    }

    /**
     * {@code DELETE  /prepayment-data-entry-files/:id} : delete the "id" prepaymentDataEntryFile.
     *
     * @param id the id of the prepaymentDataEntryFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @Override
    @DeleteMapping("/prepayment-data-entry-files/{id}")
    public ResponseEntity<Void> deletePrepaymentDataEntryFile(@PathVariable Long id) {
        log.debug("REST request to delete PrepaymentDataEntryFile : {}", id);
        prepaymentDataEntryFileService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/prepayment-data-entry-files?query=:query} : search for the prepaymentDataEntryFile corresponding to the query.
     *
     * @param query    the query of the prepaymentDataEntryFile search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @Override
    @GetMapping("/_search/prepayment-data-entry-files")
    public ResponseEntity<List<PrepaymentDataEntryFileDTO>> searchPrepaymentDataEntryFiles(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams,
                                                                                           UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of PrepaymentDataEntryFiles for query {}", query);
        Page<PrepaymentDataEntryFileDTO> page = prepaymentDataEntryFileService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
