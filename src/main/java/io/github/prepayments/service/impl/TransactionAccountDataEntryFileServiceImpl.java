package io.github.prepayments.service.impl;

import io.github.prepayments.domain.TransactionAccountDataEntryFile;
import io.github.prepayments.repository.TransactionAccountDataEntryFileRepository;
import io.github.prepayments.service.TransactionAccountDataEntryFileService;
import io.github.prepayments.service.dto.TransactionAccountDataEntryFileDTO;
import io.github.prepayments.service.mapper.TransactionAccountDataEntryFileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TransactionAccountDataEntryFile}.
 */
@Service
@Transactional
public class TransactionAccountDataEntryFileServiceImpl implements TransactionAccountDataEntryFileService {

    private final Logger log = LoggerFactory.getLogger(TransactionAccountDataEntryFileServiceImpl.class);

    private final TransactionAccountDataEntryFileRepository transactionAccountDataEntryFileRepository;

    private final TransactionAccountDataEntryFileMapper transactionAccountDataEntryFileMapper;

    public TransactionAccountDataEntryFileServiceImpl(TransactionAccountDataEntryFileRepository transactionAccountDataEntryFileRepository,
                                                      TransactionAccountDataEntryFileMapper transactionAccountDataEntryFileMapper) {
        this.transactionAccountDataEntryFileRepository = transactionAccountDataEntryFileRepository;
        this.transactionAccountDataEntryFileMapper = transactionAccountDataEntryFileMapper;
    }

    /**
     * Save a transactionAccountDataEntryFile.
     *
     * @param transactionAccountDataEntryFileDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TransactionAccountDataEntryFileDTO save(TransactionAccountDataEntryFileDTO transactionAccountDataEntryFileDTO) {
        log.debug("Request to save TransactionAccountDataEntryFile : {}", transactionAccountDataEntryFileDTO);
        TransactionAccountDataEntryFile transactionAccountDataEntryFile = transactionAccountDataEntryFileMapper.toEntity(transactionAccountDataEntryFileDTO);
        transactionAccountDataEntryFile = transactionAccountDataEntryFileRepository.save(transactionAccountDataEntryFile);
        return transactionAccountDataEntryFileMapper.toDto(transactionAccountDataEntryFile);
    }

    /**
     * Get all the transactionAccountDataEntryFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransactionAccountDataEntryFileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TransactionAccountDataEntryFiles");
        return transactionAccountDataEntryFileRepository.findAll(pageable).map(transactionAccountDataEntryFileMapper::toDto);
    }


    /**
     * Get one transactionAccountDataEntryFile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TransactionAccountDataEntryFileDTO> findOne(Long id) {
        log.debug("Request to get TransactionAccountDataEntryFile : {}", id);
        return transactionAccountDataEntryFileRepository.findById(id).map(transactionAccountDataEntryFileMapper::toDto);
    }

    /**
     * Delete the transactionAccountDataEntryFile by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TransactionAccountDataEntryFile : {}", id);
        transactionAccountDataEntryFileRepository.deleteById(id);
    }
}
