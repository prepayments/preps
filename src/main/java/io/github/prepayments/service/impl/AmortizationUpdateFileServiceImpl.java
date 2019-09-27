package io.github.prepayments.service.impl;

import io.github.prepayments.service.AmortizationUpdateFileService;
import io.github.prepayments.domain.AmortizationUpdateFile;
import io.github.prepayments.repository.AmortizationUpdateFileRepository;
import io.github.prepayments.repository.search.AmortizationUpdateFileSearchRepository;
import io.github.prepayments.service.dto.AmortizationUpdateFileDTO;
import io.github.prepayments.service.mapper.AmortizationUpdateFileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link AmortizationUpdateFile}.
 */
@Service
@Transactional
public class AmortizationUpdateFileServiceImpl implements AmortizationUpdateFileService {

    private final Logger log = LoggerFactory.getLogger(AmortizationUpdateFileServiceImpl.class);

    private final AmortizationUpdateFileRepository amortizationUpdateFileRepository;

    private final AmortizationUpdateFileMapper amortizationUpdateFileMapper;

    private final AmortizationUpdateFileSearchRepository amortizationUpdateFileSearchRepository;

    public AmortizationUpdateFileServiceImpl(AmortizationUpdateFileRepository amortizationUpdateFileRepository, AmortizationUpdateFileMapper amortizationUpdateFileMapper, AmortizationUpdateFileSearchRepository amortizationUpdateFileSearchRepository) {
        this.amortizationUpdateFileRepository = amortizationUpdateFileRepository;
        this.amortizationUpdateFileMapper = amortizationUpdateFileMapper;
        this.amortizationUpdateFileSearchRepository = amortizationUpdateFileSearchRepository;
    }

    /**
     * Save a amortizationUpdateFile.
     *
     * @param amortizationUpdateFileDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AmortizationUpdateFileDTO save(AmortizationUpdateFileDTO amortizationUpdateFileDTO) {
        log.debug("Request to save AmortizationUpdateFile : {}", amortizationUpdateFileDTO);
        AmortizationUpdateFile amortizationUpdateFile = amortizationUpdateFileMapper.toEntity(amortizationUpdateFileDTO);
        amortizationUpdateFile = amortizationUpdateFileRepository.save(amortizationUpdateFile);
        AmortizationUpdateFileDTO result = amortizationUpdateFileMapper.toDto(amortizationUpdateFile);
        amortizationUpdateFileSearchRepository.save(amortizationUpdateFile);
        return result;
    }

    /**
     * Get all the amortizationUpdateFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AmortizationUpdateFileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AmortizationUpdateFiles");
        return amortizationUpdateFileRepository.findAll(pageable)
            .map(amortizationUpdateFileMapper::toDto);
    }


    /**
     * Get one amortizationUpdateFile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AmortizationUpdateFileDTO> findOne(Long id) {
        log.debug("Request to get AmortizationUpdateFile : {}", id);
        return amortizationUpdateFileRepository.findById(id)
            .map(amortizationUpdateFileMapper::toDto);
    }

    /**
     * Delete the amortizationUpdateFile by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AmortizationUpdateFile : {}", id);
        amortizationUpdateFileRepository.deleteById(id);
        amortizationUpdateFileSearchRepository.deleteById(id);
    }

    /**
     * Search for the amortizationUpdateFile corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AmortizationUpdateFileDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AmortizationUpdateFiles for query {}", query);
        return amortizationUpdateFileSearchRepository.search(queryStringQuery(query), pageable)
            .map(amortizationUpdateFileMapper::toDto);
    }
}
