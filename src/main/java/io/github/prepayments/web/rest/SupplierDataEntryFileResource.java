package io.github.prepayments.web.rest;

import io.github.prepayments.service.SupplierDataEntryFileService;
import io.github.prepayments.web.rest.errors.BadRequestAlertException;
import io.github.prepayments.service.dto.SupplierDataEntryFileDTO;
import io.github.prepayments.service.dto.SupplierDataEntryFileCriteria;
import io.github.prepayments.service.SupplierDataEntryFileQueryService;

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

/**
 * REST controller for managing {@link io.github.prepayments.domain.SupplierDataEntryFile}.
 */
@RestController
@RequestMapping("/api")
public class SupplierDataEntryFileResource {

    private final Logger log = LoggerFactory.getLogger(SupplierDataEntryFileResource.class);

    private static final String ENTITY_NAME = "dataEntrySupplierDataEntryFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SupplierDataEntryFileService supplierDataEntryFileService;

    private final SupplierDataEntryFileQueryService supplierDataEntryFileQueryService;

    public SupplierDataEntryFileResource(SupplierDataEntryFileService supplierDataEntryFileService, SupplierDataEntryFileQueryService supplierDataEntryFileQueryService) {
        this.supplierDataEntryFileService = supplierDataEntryFileService;
        this.supplierDataEntryFileQueryService = supplierDataEntryFileQueryService;
    }

    /**
     * {@code POST  /supplier-data-entry-files} : Create a new supplierDataEntryFile.
     *
     * @param supplierDataEntryFileDTO the supplierDataEntryFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new supplierDataEntryFileDTO, or with status {@code 400 (Bad Request)} if the supplierDataEntryFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/supplier-data-entry-files")
    public ResponseEntity<SupplierDataEntryFileDTO> createSupplierDataEntryFile(@Valid @RequestBody SupplierDataEntryFileDTO supplierDataEntryFileDTO) throws URISyntaxException {
        log.debug("REST request to save SupplierDataEntryFile : {}", supplierDataEntryFileDTO);
        if (supplierDataEntryFileDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplierDataEntryFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupplierDataEntryFileDTO result = supplierDataEntryFileService.save(supplierDataEntryFileDTO);
        return ResponseEntity.created(new URI("/api/supplier-data-entry-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /supplier-data-entry-files} : Updates an existing supplierDataEntryFile.
     *
     * @param supplierDataEntryFileDTO the supplierDataEntryFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supplierDataEntryFileDTO,
     * or with status {@code 400 (Bad Request)} if the supplierDataEntryFileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the supplierDataEntryFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/supplier-data-entry-files")
    public ResponseEntity<SupplierDataEntryFileDTO> updateSupplierDataEntryFile(@Valid @RequestBody SupplierDataEntryFileDTO supplierDataEntryFileDTO) throws URISyntaxException {
        log.debug("REST request to update SupplierDataEntryFile : {}", supplierDataEntryFileDTO);
        if (supplierDataEntryFileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SupplierDataEntryFileDTO result = supplierDataEntryFileService.save(supplierDataEntryFileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, supplierDataEntryFileDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /supplier-data-entry-files} : get all the supplierDataEntryFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of supplierDataEntryFiles in body.
     */
    @GetMapping("/supplier-data-entry-files")
    public ResponseEntity<List<SupplierDataEntryFileDTO>> getAllSupplierDataEntryFiles(SupplierDataEntryFileCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get SupplierDataEntryFiles by criteria: {}", criteria);
        Page<SupplierDataEntryFileDTO> page = supplierDataEntryFileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /supplier-data-entry-files/count} : count all the supplierDataEntryFiles.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/supplier-data-entry-files/count")
    public ResponseEntity<Long> countSupplierDataEntryFiles(SupplierDataEntryFileCriteria criteria) {
        log.debug("REST request to count SupplierDataEntryFiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(supplierDataEntryFileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /supplier-data-entry-files/:id} : get the "id" supplierDataEntryFile.
     *
     * @param id the id of the supplierDataEntryFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the supplierDataEntryFileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/supplier-data-entry-files/{id}")
    public ResponseEntity<SupplierDataEntryFileDTO> getSupplierDataEntryFile(@PathVariable Long id) {
        log.debug("REST request to get SupplierDataEntryFile : {}", id);
        Optional<SupplierDataEntryFileDTO> supplierDataEntryFileDTO = supplierDataEntryFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supplierDataEntryFileDTO);
    }

    /**
     * {@code DELETE  /supplier-data-entry-files/:id} : delete the "id" supplierDataEntryFile.
     *
     * @param id the id of the supplierDataEntryFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/supplier-data-entry-files/{id}")
    public ResponseEntity<Void> deleteSupplierDataEntryFile(@PathVariable Long id) {
        log.debug("REST request to delete SupplierDataEntryFile : {}", id);
        supplierDataEntryFileService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
