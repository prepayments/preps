package io.github.prepayments.web.rest;

import io.github.prepayments.service.AmortizationUpdateFileService;
import io.github.prepayments.web.rest.errors.BadRequestAlertException;
import io.github.prepayments.service.dto.AmortizationUpdateFileDTO;
import io.github.prepayments.service.dto.AmortizationUpdateFileCriteria;
import io.github.prepayments.service.AmortizationUpdateFileQueryService;

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
import org.springframework.stereotype.Component;
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
 * REST controller for managing {@link io.github.prepayments.domain.AmortizationUpdateFile}.
 */
@Component
public class AmortizationUpdateFileResource {

    private final Logger log = LoggerFactory.getLogger(AmortizationUpdateFileResource.class);

    private static final String ENTITY_NAME = "updatesAmortizationUpdateFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AmortizationUpdateFileService amortizationUpdateFileService;

    private final AmortizationUpdateFileQueryService amortizationUpdateFileQueryService;

    public AmortizationUpdateFileResource(AmortizationUpdateFileService amortizationUpdateFileService, AmortizationUpdateFileQueryService amortizationUpdateFileQueryService) {
        this.amortizationUpdateFileService = amortizationUpdateFileService;
        this.amortizationUpdateFileQueryService = amortizationUpdateFileQueryService;
    }

    /**
     * {@code POST  /amortization-update-files} : Create a new amortizationUpdateFile.
     *
     * @param amortizationUpdateFileDTO the amortizationUpdateFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new amortizationUpdateFileDTO, or with status {@code 400 (Bad Request)} if the amortizationUpdateFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/amortization-update-files")
    public ResponseEntity<AmortizationUpdateFileDTO> createAmortizationUpdateFile(@Valid @RequestBody AmortizationUpdateFileDTO amortizationUpdateFileDTO) throws URISyntaxException {
        log.debug("REST request to save AmortizationUpdateFile : {}", amortizationUpdateFileDTO);
        if (amortizationUpdateFileDTO.getId() != null) {
            throw new BadRequestAlertException("A new amortizationUpdateFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AmortizationUpdateFileDTO result = amortizationUpdateFileService.save(amortizationUpdateFileDTO);
        return ResponseEntity.created(new URI("/api/amortization-update-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /amortization-update-files} : Updates an existing amortizationUpdateFile.
     *
     * @param amortizationUpdateFileDTO the amortizationUpdateFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated amortizationUpdateFileDTO,
     * or with status {@code 400 (Bad Request)} if the amortizationUpdateFileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the amortizationUpdateFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/amortization-update-files")
    public ResponseEntity<AmortizationUpdateFileDTO> updateAmortizationUpdateFile(@Valid @RequestBody AmortizationUpdateFileDTO amortizationUpdateFileDTO) throws URISyntaxException {
        log.debug("REST request to update AmortizationUpdateFile : {}", amortizationUpdateFileDTO);
        if (amortizationUpdateFileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AmortizationUpdateFileDTO result = amortizationUpdateFileService.save(amortizationUpdateFileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, amortizationUpdateFileDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /amortization-update-files} : get all the amortizationUpdateFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of amortizationUpdateFiles in body.
     */
    @GetMapping("/amortization-update-files")
    public ResponseEntity<List<AmortizationUpdateFileDTO>> getAllAmortizationUpdateFiles(AmortizationUpdateFileCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get AmortizationUpdateFiles by criteria: {}", criteria);
        Page<AmortizationUpdateFileDTO> page = amortizationUpdateFileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /amortization-update-files/count} : count all the amortizationUpdateFiles.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/amortization-update-files/count")
    public ResponseEntity<Long> countAmortizationUpdateFiles(AmortizationUpdateFileCriteria criteria) {
        log.debug("REST request to count AmortizationUpdateFiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(amortizationUpdateFileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /amortization-update-files/:id} : get the "id" amortizationUpdateFile.
     *
     * @param id the id of the amortizationUpdateFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the amortizationUpdateFileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/amortization-update-files/{id}")
    public ResponseEntity<AmortizationUpdateFileDTO> getAmortizationUpdateFile(@PathVariable Long id) {
        log.debug("REST request to get AmortizationUpdateFile : {}", id);
        Optional<AmortizationUpdateFileDTO> amortizationUpdateFileDTO = amortizationUpdateFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(amortizationUpdateFileDTO);
    }

    /**
     * {@code DELETE  /amortization-update-files/:id} : delete the "id" amortizationUpdateFile.
     *
     * @param id the id of the amortizationUpdateFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/amortization-update-files/{id}")
    public ResponseEntity<Void> deleteAmortizationUpdateFile(@PathVariable Long id) {
        log.debug("REST request to delete AmortizationUpdateFile : {}", id);
        amortizationUpdateFileService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/amortization-update-files?query=:query} : search for the amortizationUpdateFile corresponding
     * to the query.
     *
     * @param query the query of the amortizationUpdateFile search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/amortization-update-files")
    public ResponseEntity<List<AmortizationUpdateFileDTO>> searchAmortizationUpdateFiles(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of AmortizationUpdateFiles for query {}", query);
        Page<AmortizationUpdateFileDTO> page = amortizationUpdateFileService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
