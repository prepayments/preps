package io.github.prepayments.service.impl;

import io.github.prepayments.service.PrepaymentDataEntryFileService;
import io.github.prepayments.domain.PrepaymentDataEntryFile;
import io.github.prepayments.repository.PrepaymentDataEntryFileRepository;
import io.github.prepayments.repository.search.PrepaymentDataEntryFileSearchRepository;
import io.github.prepayments.service.dto.PrepaymentDataEntryFileDTO;
import io.github.prepayments.service.mapper.PrepaymentDataEntryFileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link PrepaymentDataEntryFile}.
 */
@Service
@Transactional
public class PrepaymentDataEntryFileServiceImpl implements PrepaymentDataEntryFileService {

    private final Logger log = LoggerFactory.getLogger(PrepaymentDataEntryFileServiceImpl.class);

    private final PrepaymentDataEntryFileRepository prepaymentDataEntryFileRepository;

    private final PrepaymentDataEntryFileMapper prepaymentDataEntryFileMapper;

    private final PrepaymentDataEntryFileSearchRepository prepaymentDataEntryFileSearchRepository;

    public PrepaymentDataEntryFileServiceImpl(PrepaymentDataEntryFileRepository prepaymentDataEntryFileRepository, PrepaymentDataEntryFileMapper prepaymentDataEntryFileMapper, PrepaymentDataEntryFileSearchRepository prepaymentDataEntryFileSearchRepository) {
        this.prepaymentDataEntryFileRepository = prepaymentDataEntryFileRepository;
        this.prepaymentDataEntryFileMapper = prepaymentDataEntryFileMapper;
        this.prepaymentDataEntryFileSearchRepository = prepaymentDataEntryFileSearchRepository;
    }

    /**
     * Save a prepaymentDataEntryFile.
     *
     * @param prepaymentDataEntryFileDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PrepaymentDataEntryFileDTO save(PrepaymentDataEntryFileDTO prepaymentDataEntryFileDTO) {
        log.debug("Request to save PrepaymentDataEntryFile : {}", prepaymentDataEntryFileDTO);
        PrepaymentDataEntryFile prepaymentDataEntryFile = prepaymentDataEntryFileMapper.toEntity(prepaymentDataEntryFileDTO);
        prepaymentDataEntryFile = prepaymentDataEntryFileRepository.save(prepaymentDataEntryFile);
        PrepaymentDataEntryFileDTO result = prepaymentDataEntryFileMapper.toDto(prepaymentDataEntryFile);
        prepaymentDataEntryFileSearchRepository.save(prepaymentDataEntryFile);
        return result;
    }

    /**
     * Get all the prepaymentDataEntryFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PrepaymentDataEntryFileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PrepaymentDataEntryFiles");
        return prepaymentDataEntryFileRepository.findAll(pageable)
            .map(prepaymentDataEntryFileMapper::toDto);
    }


    /**
     * Get one prepaymentDataEntryFile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PrepaymentDataEntryFileDTO> findOne(Long id) {
        log.debug("Request to get PrepaymentDataEntryFile : {}", id);
        return prepaymentDataEntryFileRepository.findById(id)
            .map(prepaymentDataEntryFileMapper::toDto);
    }

    /**
     * Delete the prepaymentDataEntryFile by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PrepaymentDataEntryFile : {}", id);
        prepaymentDataEntryFileRepository.deleteById(id);
        prepaymentDataEntryFileSearchRepository.deleteById(id);
    }

    /**
     * Search for the prepaymentDataEntryFile corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PrepaymentDataEntryFileDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PrepaymentDataEntryFiles for query {}", query);
        return prepaymentDataEntryFileSearchRepository.search(queryStringQuery(query), pageable)
            .map(prepaymentDataEntryFileMapper::toDto);
    }
}
