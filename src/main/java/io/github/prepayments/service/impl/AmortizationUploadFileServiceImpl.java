package io.github.prepayments.service.impl;

import io.github.prepayments.service.AmortizationUploadFileService;
import io.github.prepayments.domain.AmortizationUploadFile;
import io.github.prepayments.repository.AmortizationUploadFileRepository;
import io.github.prepayments.repository.search.AmortizationUploadFileSearchRepository;
import io.github.prepayments.service.dto.AmortizationUploadFileDTO;
import io.github.prepayments.service.mapper.AmortizationUploadFileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link AmortizationUploadFile}.
 */
@Service
@Transactional
public class AmortizationUploadFileServiceImpl implements AmortizationUploadFileService {

    private final Logger log = LoggerFactory.getLogger(AmortizationUploadFileServiceImpl.class);

    private final AmortizationUploadFileRepository amortizationUploadFileRepository;

    private final AmortizationUploadFileMapper amortizationUploadFileMapper;

    private final AmortizationUploadFileSearchRepository amortizationUploadFileSearchRepository;

    public AmortizationUploadFileServiceImpl(AmortizationUploadFileRepository amortizationUploadFileRepository, AmortizationUploadFileMapper amortizationUploadFileMapper, AmortizationUploadFileSearchRepository amortizationUploadFileSearchRepository) {
        this.amortizationUploadFileRepository = amortizationUploadFileRepository;
        this.amortizationUploadFileMapper = amortizationUploadFileMapper;
        this.amortizationUploadFileSearchRepository = amortizationUploadFileSearchRepository;
    }

    /**
     * Save a amortizationUploadFile.
     *
     * @param amortizationUploadFileDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AmortizationUploadFileDTO save(AmortizationUploadFileDTO amortizationUploadFileDTO) {
        log.debug("Request to save AmortizationUploadFile : {}", amortizationUploadFileDTO);
        AmortizationUploadFile amortizationUploadFile = amortizationUploadFileMapper.toEntity(amortizationUploadFileDTO);
        amortizationUploadFile = amortizationUploadFileRepository.save(amortizationUploadFile);
        AmortizationUploadFileDTO result = amortizationUploadFileMapper.toDto(amortizationUploadFile);
        amortizationUploadFileSearchRepository.save(amortizationUploadFile);
        return result;
    }

    /**
     * Get all the amortizationUploadFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AmortizationUploadFileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AmortizationUploadFiles");
        return amortizationUploadFileRepository.findAll(pageable)
            .map(amortizationUploadFileMapper::toDto);
    }


    /**
     * Get one amortizationUploadFile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AmortizationUploadFileDTO> findOne(Long id) {
        log.debug("Request to get AmortizationUploadFile : {}", id);
        return amortizationUploadFileRepository.findById(id)
            .map(amortizationUploadFileMapper::toDto);
    }

    /**
     * Delete the amortizationUploadFile by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AmortizationUploadFile : {}", id);
        amortizationUploadFileRepository.deleteById(id);
        amortizationUploadFileSearchRepository.deleteById(id);
    }

    /**
     * Search for the amortizationUploadFile corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AmortizationUploadFileDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AmortizationUploadFiles for query {}", query);
        return amortizationUploadFileSearchRepository.search(queryStringQuery(query), pageable)
            .map(amortizationUploadFileMapper::toDto);
    }
}
