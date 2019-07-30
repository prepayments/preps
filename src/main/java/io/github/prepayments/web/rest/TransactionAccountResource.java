package io.github.prepayments.web.rest;

import io.github.prepayments.service.TransactionAccountService;
import io.github.prepayments.web.rest.errors.BadRequestAlertException;
import io.github.prepayments.service.dto.TransactionAccountDTO;
import io.github.prepayments.service.dto.TransactionAccountCriteria;
import io.github.prepayments.service.TransactionAccountQueryService;

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
 * REST controller for managing {@link io.github.prepayments.domain.TransactionAccount}.
 */
@RestController
@RequestMapping("/api")
public class TransactionAccountResource {

    private final Logger log = LoggerFactory.getLogger(TransactionAccountResource.class);

    private static final String ENTITY_NAME = "prepaymentsTransactionAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransactionAccountService transactionAccountService;

    private final TransactionAccountQueryService transactionAccountQueryService;

    public TransactionAccountResource(TransactionAccountService transactionAccountService, TransactionAccountQueryService transactionAccountQueryService) {
        this.transactionAccountService = transactionAccountService;
        this.transactionAccountQueryService = transactionAccountQueryService;
    }

    /**
     * {@code POST  /transaction-accounts} : Create a new transactionAccount.
     *
     * @param transactionAccountDTO the transactionAccountDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transactionAccountDTO, or with status {@code 400 (Bad Request)} if the transactionAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transaction-accounts")
    public ResponseEntity<TransactionAccountDTO> createTransactionAccount(@Valid @RequestBody TransactionAccountDTO transactionAccountDTO) throws URISyntaxException {
        log.debug("REST request to save TransactionAccount : {}", transactionAccountDTO);
        if (transactionAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new transactionAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransactionAccountDTO result = transactionAccountService.save(transactionAccountDTO);
        return ResponseEntity.created(new URI("/api/transaction-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transaction-accounts} : Updates an existing transactionAccount.
     *
     * @param transactionAccountDTO the transactionAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionAccountDTO,
     * or with status {@code 400 (Bad Request)} if the transactionAccountDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transactionAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transaction-accounts")
    public ResponseEntity<TransactionAccountDTO> updateTransactionAccount(@Valid @RequestBody TransactionAccountDTO transactionAccountDTO) throws URISyntaxException {
        log.debug("REST request to update TransactionAccount : {}", transactionAccountDTO);
        if (transactionAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TransactionAccountDTO result = transactionAccountService.save(transactionAccountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, transactionAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transaction-accounts} : get all the transactionAccounts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactionAccounts in body.
     */
    @GetMapping("/transaction-accounts")
    public ResponseEntity<List<TransactionAccountDTO>> getAllTransactionAccounts(TransactionAccountCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get TransactionAccounts by criteria: {}", criteria);
        Page<TransactionAccountDTO> page = transactionAccountQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /transaction-accounts/count} : count all the transactionAccounts.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/transaction-accounts/count")
    public ResponseEntity<Long> countTransactionAccounts(TransactionAccountCriteria criteria) {
        log.debug("REST request to count TransactionAccounts by criteria: {}", criteria);
        return ResponseEntity.ok().body(transactionAccountQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /transaction-accounts/:id} : get the "id" transactionAccount.
     *
     * @param id the id of the transactionAccountDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transactionAccountDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transaction-accounts/{id}")
    public ResponseEntity<TransactionAccountDTO> getTransactionAccount(@PathVariable Long id) {
        log.debug("REST request to get TransactionAccount : {}", id);
        Optional<TransactionAccountDTO> transactionAccountDTO = transactionAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transactionAccountDTO);
    }

    /**
     * {@code DELETE  /transaction-accounts/:id} : delete the "id" transactionAccount.
     *
     * @param id the id of the transactionAccountDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transaction-accounts/{id}")
    public ResponseEntity<Void> deleteTransactionAccount(@PathVariable Long id) {
        log.debug("REST request to delete TransactionAccount : {}", id);
        transactionAccountService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/transaction-accounts?query=:query} : search for the transactionAccount corresponding
     * to the query.
     *
     * @param query the query of the transactionAccount search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/transaction-accounts")
    public ResponseEntity<List<TransactionAccountDTO>> searchTransactionAccounts(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of TransactionAccounts for query {}", query);
        Page<TransactionAccountDTO> page = transactionAccountService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
