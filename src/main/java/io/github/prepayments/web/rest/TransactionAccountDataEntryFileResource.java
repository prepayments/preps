package io.github.prepayments.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.github.prepayments.service.TransactionAccountDataEntryFileQueryService;
import io.github.prepayments.service.TransactionAccountDataEntryFileService;
import io.github.prepayments.service.dto.TransactionAccountDataEntryFileCriteria;
import io.github.prepayments.service.dto.TransactionAccountDataEntryFileDTO;
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
 * REST controller for managing {@link io.github.prepayments.domain.TransactionAccountDataEntryFile}.
 */
//@Component("transactionAccountDataEntryFileResourceDelegate")
@RestController
@RequestMapping("/api")
public class TransactionAccountDataEntryFileResource {

    private static final String ENTITY_NAME = "dataEntryTransactionAccountDataEntryFile";
    private final Logger log = LoggerFactory.getLogger(TransactionAccountDataEntryFileResource.class);
    private final TransactionAccountDataEntryFileService transactionAccountDataEntryFileService;
    private final TransactionAccountDataEntryFileQueryService transactionAccountDataEntryFileQueryService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public TransactionAccountDataEntryFileResource(TransactionAccountDataEntryFileService transactionAccountDataEntryFileService,
                                                   TransactionAccountDataEntryFileQueryService transactionAccountDataEntryFileQueryService) {
        this.transactionAccountDataEntryFileService = transactionAccountDataEntryFileService;
        this.transactionAccountDataEntryFileQueryService = transactionAccountDataEntryFileQueryService;
    }

    /**
     * {@code POST  /transaction-account-data-entry-files} : Create a new transactionAccountDataEntryFile.
     *
     * @param transactionAccountDataEntryFileDTO the transactionAccountDataEntryFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transactionAccountDataEntryFileDTO, or with status {@code 400 (Bad Request)} if the
     * transactionAccountDataEntryFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transaction-account-data-entry-files")
    public ResponseEntity<TransactionAccountDataEntryFileDTO> createTransactionAccountDataEntryFile(@Valid @RequestBody TransactionAccountDataEntryFileDTO transactionAccountDataEntryFileDTO)
        throws URISyntaxException {
        log.debug("REST request to save TransactionAccountDataEntryFile : {}", transactionAccountDataEntryFileDTO);
        if (transactionAccountDataEntryFileDTO.getId() != null) {
            throw new BadRequestAlertException("A new transactionAccountDataEntryFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransactionAccountDataEntryFileDTO result = transactionAccountDataEntryFileService.save(transactionAccountDataEntryFileDTO);
        return ResponseEntity.created(new URI("/api/transaction-account-data-entry-files/" + result.getId()))
                             .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                             .body(result);
    }

    /**
     * {@code PUT  /transaction-account-data-entry-files} : Updates an existing transactionAccountDataEntryFile.
     *
     * @param transactionAccountDataEntryFileDTO the transactionAccountDataEntryFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionAccountDataEntryFileDTO, or with status {@code 400 (Bad Request)} if the
     * transactionAccountDataEntryFileDTO is not valid, or with status {@code 500 (Internal Server Error)} if the transactionAccountDataEntryFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transaction-account-data-entry-files")
    public ResponseEntity<TransactionAccountDataEntryFileDTO> updateTransactionAccountDataEntryFile(@Valid @RequestBody TransactionAccountDataEntryFileDTO transactionAccountDataEntryFileDTO)
        throws URISyntaxException {
        log.debug("REST request to update TransactionAccountDataEntryFile : {}", transactionAccountDataEntryFileDTO);
        if (transactionAccountDataEntryFileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TransactionAccountDataEntryFileDTO result = transactionAccountDataEntryFileService.save(transactionAccountDataEntryFileDTO);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, transactionAccountDataEntryFileDTO.getId().toString())).body(result);
    }

    /**
     * {@code GET  /transaction-account-data-entry-files} : get all the transactionAccountDataEntryFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactionAccountDataEntryFiles in body.
     */
    @GetMapping("/transaction-account-data-entry-files")
    public ResponseEntity<List<TransactionAccountDataEntryFileDTO>> getAllTransactionAccountDataEntryFiles(TransactionAccountDataEntryFileCriteria criteria, Pageable pageable,
                                                                                                           @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get TransactionAccountDataEntryFiles by criteria: {}", criteria);
        Page<TransactionAccountDataEntryFileDTO> page = transactionAccountDataEntryFileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /transaction-account-data-entry-files/count} : count all the transactionAccountDataEntryFiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/transaction-account-data-entry-files/count")
    public ResponseEntity<Long> countTransactionAccountDataEntryFiles(TransactionAccountDataEntryFileCriteria criteria) {
        log.debug("REST request to count TransactionAccountDataEntryFiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(transactionAccountDataEntryFileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /transaction-account-data-entry-files/:id} : get the "id" transactionAccountDataEntryFile.
     *
     * @param id the id of the transactionAccountDataEntryFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transactionAccountDataEntryFileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transaction-account-data-entry-files/{id}")
    public ResponseEntity<TransactionAccountDataEntryFileDTO> getTransactionAccountDataEntryFile(@PathVariable Long id) {
        log.debug("REST request to get TransactionAccountDataEntryFile : {}", id);
        Optional<TransactionAccountDataEntryFileDTO> transactionAccountDataEntryFileDTO = transactionAccountDataEntryFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transactionAccountDataEntryFileDTO);
    }

    /**
     * {@code DELETE  /transaction-account-data-entry-files/:id} : delete the "id" transactionAccountDataEntryFile.
     *
     * @param id the id of the transactionAccountDataEntryFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transaction-account-data-entry-files/{id}")
    public ResponseEntity<Void> deleteTransactionAccountDataEntryFile(@PathVariable Long id) {
        log.debug("REST request to delete TransactionAccountDataEntryFile : {}", id);
        transactionAccountDataEntryFileService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/transaction-account-data-entry-files?query=:query} : search for the transactionAccountDataEntryFile corresponding to the query.
     *
     * @param query    the query of the transactionAccountDataEntryFile search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/transaction-account-data-entry-files")
    public ResponseEntity<List<TransactionAccountDataEntryFileDTO>> searchTransactionAccountDataEntryFiles(@RequestParam String query, Pageable pageable,
                                                                                                           @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of TransactionAccountDataEntryFiles for query {}", query);
        Page<TransactionAccountDataEntryFileDTO> page = transactionAccountDataEntryFileService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
