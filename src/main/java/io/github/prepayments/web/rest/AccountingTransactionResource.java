package io.github.prepayments.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.github.prepayments.service.AccountingTransactionQueryService;
import io.github.prepayments.service.AccountingTransactionService;
import io.github.prepayments.service.dto.AccountingTransactionCriteria;
import io.github.prepayments.service.dto.AccountingTransactionDTO;
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
 * REST controller for managing {@link io.github.prepayments.domain.AccountingTransaction}.
 */
@RestController
@RequestMapping("/api")
public class AccountingTransactionResource {

    private final Logger log = LoggerFactory.getLogger(AccountingTransactionResource.class);

    private static final String ENTITY_NAME = "prepaymentsAccountingTransaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccountingTransactionService accountingTransactionService;

    private final AccountingTransactionQueryService accountingTransactionQueryService;

    public AccountingTransactionResource(AccountingTransactionService accountingTransactionService, AccountingTransactionQueryService accountingTransactionQueryService) {
        this.accountingTransactionService = accountingTransactionService;
        this.accountingTransactionQueryService = accountingTransactionQueryService;
    }

    /**
     * {@code POST  /accounting-transactions} : Create a new accountingTransaction.
     *
     * @param accountingTransactionDTO the accountingTransactionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accountingTransactionDTO, or with status {@code 400 (Bad Request)} if the accountingTransaction has
     * already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/accounting-transactions")
    public ResponseEntity<AccountingTransactionDTO> createAccountingTransaction(@Valid @RequestBody AccountingTransactionDTO accountingTransactionDTO) throws URISyntaxException {
        log.debug("REST request to save AccountingTransaction : {}", accountingTransactionDTO);
        if (accountingTransactionDTO.getId() != null) {
            throw new BadRequestAlertException("A new accountingTransaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccountingTransactionDTO result = accountingTransactionService.save(accountingTransactionDTO);
        return ResponseEntity.created(new URI("/api/accounting-transactions/" + result.getId()))
                             .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                             .body(result);
    }

    /**
     * {@code PUT  /accounting-transactions} : Updates an existing accountingTransaction.
     *
     * @param accountingTransactionDTO the accountingTransactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountingTransactionDTO,
     * or with status {@code 400 (Bad Request)} if the accountingTransactionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accountingTransactionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/accounting-transactions")
    public ResponseEntity<AccountingTransactionDTO> updateAccountingTransaction(@Valid @RequestBody AccountingTransactionDTO accountingTransactionDTO) throws URISyntaxException {
        log.debug("REST request to update AccountingTransaction : {}", accountingTransactionDTO);
        if (accountingTransactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AccountingTransactionDTO result = accountingTransactionService.save(accountingTransactionDTO);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, accountingTransactionDTO.getId().toString())).body(result);
    }

    /**
     * {@code GET  /accounting-transactions} : get all the accountingTransactions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accountingTransactions in body.
     */
    @GetMapping("/accounting-transactions")
    public ResponseEntity<List<AccountingTransactionDTO>> getAllAccountingTransactions(AccountingTransactionCriteria criteria, Pageable pageable,
                                                                                       @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get AccountingTransactions by criteria: {}", criteria);
        Page<AccountingTransactionDTO> page = accountingTransactionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /accounting-transactions/count} : count all the accountingTransactions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/accounting-transactions/count")
    public ResponseEntity<Long> countAccountingTransactions(AccountingTransactionCriteria criteria) {
        log.debug("REST request to count AccountingTransactions by criteria: {}", criteria);
        return ResponseEntity.ok().body(accountingTransactionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /accounting-transactions/:id} : get the "id" accountingTransaction.
     *
     * @param id the id of the accountingTransactionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accountingTransactionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/accounting-transactions/{id}")
    public ResponseEntity<AccountingTransactionDTO> getAccountingTransaction(@PathVariable Long id) {
        log.debug("REST request to get AccountingTransaction : {}", id);
        Optional<AccountingTransactionDTO> accountingTransactionDTO = accountingTransactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accountingTransactionDTO);
    }

    /**
     * {@code DELETE  /accounting-transactions/:id} : delete the "id" accountingTransaction.
     *
     * @param id the id of the accountingTransactionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/accounting-transactions/{id}")
    public ResponseEntity<Void> deleteAccountingTransaction(@PathVariable Long id) {
        log.debug("REST request to delete AccountingTransaction : {}", id);
        accountingTransactionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/accounting-transactions?query=:query} : search for the accountingTransaction corresponding
     * to the query.
     *
     * @param query the query of the accountingTransaction search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/accounting-transactions")
    public ResponseEntity<List<AccountingTransactionDTO>> searchAccountingTransactions(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams,
                                                                                       UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of AccountingTransactions for query {}", query);
        Page<AccountingTransactionDTO> page = accountingTransactionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
