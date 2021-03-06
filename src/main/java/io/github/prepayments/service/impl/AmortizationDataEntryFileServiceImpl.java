package io.github.prepayments.service.impl;

import io.github.prepayments.service.AmortizationDataEntryFileService;
import io.github.prepayments.domain.AmortizationDataEntryFile;
import io.github.prepayments.repository.AmortizationDataEntryFileRepository;
import io.github.prepayments.repository.search.AmortizationDataEntryFileSearchRepository;
import io.github.prepayments.service.dto.AmortizationDataEntryFileDTO;
import io.github.prepayments.service.mapper.AmortizationDataEntryFileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link AmortizationDataEntryFile}.
 */
@Service
@Transactional
public class AmortizationDataEntryFileServiceImpl implements AmortizationDataEntryFileService {

    private final Logger log = LoggerFactory.getLogger(AmortizationDataEntryFileServiceImpl.class);

    private final AmortizationDataEntryFileRepository amortizationDataEntryFileRepository;

    private final AmortizationDataEntryFileMapper amortizationDataEntryFileMapper;

    private final AmortizationDataEntryFileSearchRepository amortizationDataEntryFileSearchRepository;

    public AmortizationDataEntryFileServiceImpl(AmortizationDataEntryFileRepository amortizationDataEntryFileRepository, AmortizationDataEntryFileMapper amortizationDataEntryFileMapper, AmortizationDataEntryFileSearchRepository amortizationDataEntryFileSearchRepository) {
        this.amortizationDataEntryFileRepository = amortizationDataEntryFileRepository;
        this.amortizationDataEntryFileMapper = amortizationDataEntryFileMapper;
        this.amortizationDataEntryFileSearchRepository = amortizationDataEntryFileSearchRepository;
    }

    /**
     * Save a amortizationDataEntryFile.
     *
     * @param amortizationDataEntryFileDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AmortizationDataEntryFileDTO save(AmortizationDataEntryFileDTO amortizationDataEntryFileDTO) {
        log.debug("Request to save AmortizationDataEntryFile : {}", amortizationDataEntryFileDTO);
        AmortizationDataEntryFile amortizationDataEntryFile = amortizationDataEntryFileMapper.toEntity(amortizationDataEntryFileDTO);
        amortizationDataEntryFile = amortizationDataEntryFileRepository.save(amortizationDataEntryFile);
        AmortizationDataEntryFileDTO result = amortizationDataEntryFileMapper.toDto(amortizationDataEntryFile);
        amortizationDataEntryFileSearchRepository.save(amortizationDataEntryFile);
        return result;
    }

    /**
     * Get all the amortizationDataEntryFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AmortizationDataEntryFileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AmortizationDataEntryFiles");
        return amortizationDataEntryFileRepository.findAll(pageable)
            .map(amortizationDataEntryFileMapper::toDto);
    }


    /**
     * Get one amortizationDataEntryFile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AmortizationDataEntryFileDTO> findOne(Long id) {
        log.debug("Request to get AmortizationDataEntryFile : {}", id);
        return amortizationDataEntryFileRepository.findById(id)
            .map(amortizationDataEntryFileMapper::toDto);
    }

    /**
     * Delete the amortizationDataEntryFile by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AmortizationDataEntryFile : {}", id);
        amortizationDataEntryFileRepository.deleteById(id);
        amortizationDataEntryFileSearchRepository.deleteById(id);
    }

    /**
     * Search for the amortizationDataEntryFile corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AmortizationDataEntryFileDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AmortizationDataEntryFiles for query {}", query);
        return amortizationDataEntryFileSearchRepository.search(queryStringQuery(query), pageable)
            .map(amortizationDataEntryFileMapper::toDto);
    }
}
