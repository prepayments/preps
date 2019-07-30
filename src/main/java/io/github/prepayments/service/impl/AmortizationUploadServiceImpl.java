package io.github.prepayments.service.impl;

import io.github.prepayments.service.AmortizationUploadService;
import io.github.prepayments.domain.AmortizationUpload;
import io.github.prepayments.repository.AmortizationUploadRepository;
import io.github.prepayments.repository.search.AmortizationUploadSearchRepository;
import io.github.prepayments.service.dto.AmortizationUploadDTO;
import io.github.prepayments.service.mapper.AmortizationUploadMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link AmortizationUpload}.
 */
@Service
@Transactional
public class AmortizationUploadServiceImpl implements AmortizationUploadService {

    private final Logger log = LoggerFactory.getLogger(AmortizationUploadServiceImpl.class);

    private final AmortizationUploadRepository amortizationUploadRepository;

    private final AmortizationUploadMapper amortizationUploadMapper;

    private final AmortizationUploadSearchRepository amortizationUploadSearchRepository;

    public AmortizationUploadServiceImpl(AmortizationUploadRepository amortizationUploadRepository, AmortizationUploadMapper amortizationUploadMapper, AmortizationUploadSearchRepository amortizationUploadSearchRepository) {
        this.amortizationUploadRepository = amortizationUploadRepository;
        this.amortizationUploadMapper = amortizationUploadMapper;
        this.amortizationUploadSearchRepository = amortizationUploadSearchRepository;
    }

    /**
     * Save a amortizationUpload.
     *
     * @param amortizationUploadDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AmortizationUploadDTO save(AmortizationUploadDTO amortizationUploadDTO) {
        log.debug("Request to save AmortizationUpload : {}", amortizationUploadDTO);
        AmortizationUpload amortizationUpload = amortizationUploadMapper.toEntity(amortizationUploadDTO);
        amortizationUpload = amortizationUploadRepository.save(amortizationUpload);
        AmortizationUploadDTO result = amortizationUploadMapper.toDto(amortizationUpload);
        amortizationUploadSearchRepository.save(amortizationUpload);
        return result;
    }

    /**
     * Get all the amortizationUploads.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AmortizationUploadDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AmortizationUploads");
        return amortizationUploadRepository.findAll(pageable)
            .map(amortizationUploadMapper::toDto);
    }


    /**
     * Get one amortizationUpload by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AmortizationUploadDTO> findOne(Long id) {
        log.debug("Request to get AmortizationUpload : {}", id);
        return amortizationUploadRepository.findById(id)
            .map(amortizationUploadMapper::toDto);
    }

    /**
     * Delete the amortizationUpload by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AmortizationUpload : {}", id);
        amortizationUploadRepository.deleteById(id);
        amortizationUploadSearchRepository.deleteById(id);
    }

    /**
     * Search for the amortizationUpload corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AmortizationUploadDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AmortizationUploads for query {}", query);
        return amortizationUploadSearchRepository.search(queryStringQuery(query), pageable)
            .map(amortizationUploadMapper::toDto);
    }
}
