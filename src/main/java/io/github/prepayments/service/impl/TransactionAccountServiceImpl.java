package io.github.prepayments.service.impl;

import io.github.prepayments.service.TransactionAccountService;
import io.github.prepayments.domain.TransactionAccount;
import io.github.prepayments.repository.TransactionAccountRepository;
import io.github.prepayments.service.dto.TransactionAccountDTO;
import io.github.prepayments.service.mapper.TransactionAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TransactionAccount}.
 */
@Service
@Transactional
public class TransactionAccountServiceImpl implements TransactionAccountService {

    private final Logger log = LoggerFactory.getLogger(TransactionAccountServiceImpl.class);

    private final TransactionAccountRepository transactionAccountRepository;

    private final TransactionAccountMapper transactionAccountMapper;

    public TransactionAccountServiceImpl(TransactionAccountRepository transactionAccountRepository, TransactionAccountMapper transactionAccountMapper) {
        this.transactionAccountRepository = transactionAccountRepository;
        this.transactionAccountMapper = transactionAccountMapper;
    }

    /**
     * Save a transactionAccount.
     *
     * @param transactionAccountDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TransactionAccountDTO save(TransactionAccountDTO transactionAccountDTO) {
        log.debug("Request to save TransactionAccount : {}", transactionAccountDTO);
        TransactionAccount transactionAccount = transactionAccountMapper.toEntity(transactionAccountDTO);
        transactionAccount = transactionAccountRepository.save(transactionAccount);
        return transactionAccountMapper.toDto(transactionAccount);
    }

    /**
     * Get all the transactionAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransactionAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TransactionAccounts");
        return transactionAccountRepository.findAll(pageable)
            .map(transactionAccountMapper::toDto);
    }


    /**
     * Get one transactionAccount by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TransactionAccountDTO> findOne(Long id) {
        log.debug("Request to get TransactionAccount : {}", id);
        return transactionAccountRepository.findById(id)
            .map(transactionAccountMapper::toDto);
    }

    /**
     * Delete the transactionAccount by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TransactionAccount : {}", id);
        transactionAccountRepository.deleteById(id);
    }
}
