package io.github.prepayments.service;

import io.github.prepayments.service.dto.AccountingTransactionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.prepayments.domain.AccountingTransaction}.
 */
public interface AccountingTransactionService {

    /**
     * Save a accountingTransaction.
     *
     * @param accountingTransactionDTO the entity to save.
     * @return the persisted entity.
     */
    AccountingTransactionDTO save(AccountingTransactionDTO accountingTransactionDTO);

    /**
     * Get all the accountingTransactions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AccountingTransactionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" accountingTransaction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AccountingTransactionDTO> findOne(Long id);

    /**
     * Delete the "id" accountingTransaction.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the accountingTransaction corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AccountingTransactionDTO> search(String query, Pageable pageable);
}
