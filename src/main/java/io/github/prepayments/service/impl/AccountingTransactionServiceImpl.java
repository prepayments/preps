package io.github.prepayments.service.impl;

import io.github.prepayments.service.AccountingTransactionService;
import io.github.prepayments.domain.AccountingTransaction;
import io.github.prepayments.repository.AccountingTransactionRepository;
import io.github.prepayments.service.dto.AccountingTransactionDTO;
import io.github.prepayments.service.mapper.AccountingTransactionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AccountingTransaction}.
 */
@Service
@Transactional
public class AccountingTransactionServiceImpl implements AccountingTransactionService {

    private final Logger log = LoggerFactory.getLogger(AccountingTransactionServiceImpl.class);

    private final AccountingTransactionRepository accountingTransactionRepository;

    private final AccountingTransactionMapper accountingTransactionMapper;

    public AccountingTransactionServiceImpl(AccountingTransactionRepository accountingTransactionRepository, AccountingTransactionMapper accountingTransactionMapper) {
        this.accountingTransactionRepository = accountingTransactionRepository;
        this.accountingTransactionMapper = accountingTransactionMapper;
    }

    /**
     * Save a accountingTransaction.
     *
     * @param accountingTransactionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AccountingTransactionDTO save(AccountingTransactionDTO accountingTransactionDTO) {
        log.debug("Request to save AccountingTransaction : {}", accountingTransactionDTO);
        AccountingTransaction accountingTransaction = accountingTransactionMapper.toEntity(accountingTransactionDTO);
        accountingTransaction = accountingTransactionRepository.save(accountingTransaction);
        return accountingTransactionMapper.toDto(accountingTransaction);
    }

    /**
     * Get all the accountingTransactions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AccountingTransactionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AccountingTransactions");
        return accountingTransactionRepository.findAll(pageable)
            .map(accountingTransactionMapper::toDto);
    }


    /**
     * Get one accountingTransaction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AccountingTransactionDTO> findOne(Long id) {
        log.debug("Request to get AccountingTransaction : {}", id);
        return accountingTransactionRepository.findById(id)
            .map(accountingTransactionMapper::toDto);
    }

    /**
     * Delete the accountingTransaction by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AccountingTransaction : {}", id);
        accountingTransactionRepository.deleteById(id);
    }
}
