package io.github.prepayments.service.impl;

import io.github.prepayments.service.AmortizationEntryUpdateService;
import io.github.prepayments.domain.AmortizationEntryUpdate;
import io.github.prepayments.repository.AmortizationEntryUpdateRepository;
import io.github.prepayments.repository.search.AmortizationEntryUpdateSearchRepository;
import io.github.prepayments.service.dto.AmortizationEntryUpdateDTO;
import io.github.prepayments.service.mapper.AmortizationEntryUpdateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link AmortizationEntryUpdate}.
 */
@Service
@Transactional
public class AmortizationEntryUpdateServiceImpl implements AmortizationEntryUpdateService {

    private final Logger log = LoggerFactory.getLogger(AmortizationEntryUpdateServiceImpl.class);

    private final AmortizationEntryUpdateRepository amortizationEntryUpdateRepository;

    private final AmortizationEntryUpdateMapper amortizationEntryUpdateMapper;

    private final AmortizationEntryUpdateSearchRepository amortizationEntryUpdateSearchRepository;

    public AmortizationEntryUpdateServiceImpl(AmortizationEntryUpdateRepository amortizationEntryUpdateRepository, AmortizationEntryUpdateMapper amortizationEntryUpdateMapper, AmortizationEntryUpdateSearchRepository amortizationEntryUpdateSearchRepository) {
        this.amortizationEntryUpdateRepository = amortizationEntryUpdateRepository;
        this.amortizationEntryUpdateMapper = amortizationEntryUpdateMapper;
        this.amortizationEntryUpdateSearchRepository = amortizationEntryUpdateSearchRepository;
    }

    /**
     * Save a amortizationEntryUpdate.
     *
     * @param amortizationEntryUpdateDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AmortizationEntryUpdateDTO save(AmortizationEntryUpdateDTO amortizationEntryUpdateDTO) {
        log.debug("Request to save AmortizationEntryUpdate : {}", amortizationEntryUpdateDTO);
        AmortizationEntryUpdate amortizationEntryUpdate = amortizationEntryUpdateMapper.toEntity(amortizationEntryUpdateDTO);
        amortizationEntryUpdate = amortizationEntryUpdateRepository.save(amortizationEntryUpdate);
        AmortizationEntryUpdateDTO result = amortizationEntryUpdateMapper.toDto(amortizationEntryUpdate);
        amortizationEntryUpdateSearchRepository.save(amortizationEntryUpdate);
        return result;
    }

    /**
     * Get all the amortizationEntryUpdates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AmortizationEntryUpdateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AmortizationEntryUpdates");
        return amortizationEntryUpdateRepository.findAll(pageable)
            .map(amortizationEntryUpdateMapper::toDto);
    }


    /**
     * Get one amortizationEntryUpdate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AmortizationEntryUpdateDTO> findOne(Long id) {
        log.debug("Request to get AmortizationEntryUpdate : {}", id);
        return amortizationEntryUpdateRepository.findById(id)
            .map(amortizationEntryUpdateMapper::toDto);
    }

    /**
     * Delete the amortizationEntryUpdate by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AmortizationEntryUpdate : {}", id);
        amortizationEntryUpdateRepository.deleteById(id);
        amortizationEntryUpdateSearchRepository.deleteById(id);
    }

    /**
     * Search for the amortizationEntryUpdate corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AmortizationEntryUpdateDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AmortizationEntryUpdates for query {}", query);
        return amortizationEntryUpdateSearchRepository.search(queryStringQuery(query), pageable)
            .map(amortizationEntryUpdateMapper::toDto);
    }
}
