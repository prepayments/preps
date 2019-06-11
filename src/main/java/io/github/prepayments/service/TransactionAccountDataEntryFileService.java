package io.github.prepayments.service;

import io.github.prepayments.service.dto.TransactionAccountDataEntryFileDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.prepayments.domain.TransactionAccountDataEntryFile}.
 */
public interface TransactionAccountDataEntryFileService {

    /**
     * Save a transactionAccountDataEntryFile.
     *
     * @param transactionAccountDataEntryFileDTO the entity to save.
     * @return the persisted entity.
     */
    TransactionAccountDataEntryFileDTO save(TransactionAccountDataEntryFileDTO transactionAccountDataEntryFileDTO);

    /**
     * Get all the transactionAccountDataEntryFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TransactionAccountDataEntryFileDTO> findAll(Pageable pageable);


    /**
     * Get the "id" transactionAccountDataEntryFile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TransactionAccountDataEntryFileDTO> findOne(Long id);

    /**
     * Delete the "id" transactionAccountDataEntryFile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
