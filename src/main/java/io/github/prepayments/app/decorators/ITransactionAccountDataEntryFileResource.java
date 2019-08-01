package io.github.prepayments.app.decorators;

import io.github.prepayments.service.dto.TransactionAccountDataEntryFileCriteria;
import io.github.prepayments.service.dto.TransactionAccountDataEntryFileDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

public interface ITransactionAccountDataEntryFileResource {
    /**
     * {@code POST  /transaction-account-data-entry-files} : Create a new transactionAccountDataEntryFile.
     *
     * @param transactionAccountDataEntryFileDTO the transactionAccountDataEntryFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transactionAccountDataEntryFileDTO, or with status {@code 400 (Bad Request)} if the
     * transactionAccountDataEntryFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    ResponseEntity<TransactionAccountDataEntryFileDTO> createTransactionAccountDataEntryFile(@Valid TransactionAccountDataEntryFileDTO transactionAccountDataEntryFileDTO)
        throws URISyntaxException;

    /**
     * {@code PUT  /transaction-account-data-entry-files} : Updates an existing transactionAccountDataEntryFile.
     *
     * @param transactionAccountDataEntryFileDTO the transactionAccountDataEntryFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionAccountDataEntryFileDTO, or with status {@code 400 (Bad Request)} if the
     * transactionAccountDataEntryFileDTO is not valid, or with status {@code 500 (Internal Server Error)} if the transactionAccountDataEntryFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    ResponseEntity<TransactionAccountDataEntryFileDTO> updateTransactionAccountDataEntryFile(@Valid TransactionAccountDataEntryFileDTO transactionAccountDataEntryFileDTO)
        throws URISyntaxException;

    /**
     * {@code GET  /transaction-account-data-entry-files} : get all the transactionAccountDataEntryFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactionAccountDataEntryFiles in body.
     */
    ResponseEntity<List<TransactionAccountDataEntryFileDTO>> getAllTransactionAccountDataEntryFiles(TransactionAccountDataEntryFileCriteria criteria, Pageable pageable,
                                                                                                    MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder);

    /**
     * {@code GET  /transaction-account-data-entry-files/count} : count all the transactionAccountDataEntryFiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    ResponseEntity<Long> countTransactionAccountDataEntryFiles(TransactionAccountDataEntryFileCriteria criteria);

    /**
     * {@code GET  /transaction-account-data-entry-files/:id} : get the "id" transactionAccountDataEntryFile.
     *
     * @param id the id of the transactionAccountDataEntryFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transactionAccountDataEntryFileDTO, or with status {@code 404 (Not Found)}.
     */
    ResponseEntity<TransactionAccountDataEntryFileDTO> getTransactionAccountDataEntryFile(Long id);

    /**
     * {@code DELETE  /transaction-account-data-entry-files/:id} : delete the "id" transactionAccountDataEntryFile.
     *
     * @param id the id of the transactionAccountDataEntryFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    ResponseEntity<Void> deleteTransactionAccountDataEntryFile(Long id);

    /**
     * {@code SEARCH  /_search/transaction-account-data-entry-files?query=:query} : search for the transactionAccountDataEntryFile corresponding to the query.
     *
     * @param query    the query of the transactionAccountDataEntryFile search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    ResponseEntity<List<TransactionAccountDataEntryFileDTO>> searchTransactionAccountDataEntryFiles(String query, Pageable pageable, MultiValueMap<String, String> queryParams, UriComponentsBuilder
        uriBuilder);
}
