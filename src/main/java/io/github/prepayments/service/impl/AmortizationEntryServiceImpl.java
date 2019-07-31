package io.github.prepayments.service.impl;

import io.github.prepayments.domain.AmortizationEntry;
import io.github.prepayments.repository.AmortizationEntryRepository;
import io.github.prepayments.repository.search.AmortizationEntrySearchRepository;
import io.github.prepayments.service.AmortizationEntryService;
import io.github.prepayments.service.dto.AmortizationEntryDTO;
import io.github.prepayments.service.mapper.AmortizationEntryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link AmortizationEntry}.
 */
@Service
@Transactional
public class AmortizationEntryServiceImpl implements AmortizationEntryService {

    private final Logger log = LoggerFactory.getLogger(AmortizationEntryServiceImpl.class);

    private final AmortizationEntryRepository amortizationEntryRepository;
    private final AmortizationEntryMapper amortizationEntryMapper;
    private final AmortizationEntrySearchRepository amortizationEntrySearchRepository;

    public AmortizationEntryServiceImpl(AmortizationEntryRepository amortizationEntryRepository, AmortizationEntryMapper amortizationEntryMapper,
                                        AmortizationEntrySearchRepository amortizationEntrySearchRepository) {
        this.amortizationEntryRepository = amortizationEntryRepository;
        this.amortizationEntryMapper = amortizationEntryMapper;
        this.amortizationEntrySearchRepository = amortizationEntrySearchRepository;
    }

    /**
     * Save a amortizationEntry.
     *
     * @param amortizationEntryDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AmortizationEntryDTO save(AmortizationEntryDTO amortizationEntryDTO) {
        log.debug("Request to save AmortizationEntry : {}", amortizationEntryDTO);
        AmortizationEntry amortizationEntry = amortizationEntryMapper.toEntity(amortizationEntryDTO);
        amortizationEntry = amortizationEntryRepository.save(amortizationEntry);
        AmortizationEntryDTO result = amortizationEntryMapper.toDto(amortizationEntry);
        amortizationEntrySearchRepository.save(amortizationEntry);
        return result;
    }

    /**
     * Get all the amortizationEntries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AmortizationEntryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AmortizationEntries");
        return amortizationEntryRepository.findAll(pageable).map(amortizationEntryMapper::toDto);
    }


    /**
     * Get one amortizationEntry by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AmortizationEntryDTO> findOne(Long id) {
        log.debug("Request to get AmortizationEntry : {}", id);
        return amortizationEntryRepository.findById(id).map(amortizationEntryMapper::toDto);
    }

    /**
     * Delete the amortizationEntry by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AmortizationEntry : {}", id);
        amortizationEntryRepository.deleteById(id);
        amortizationEntrySearchRepository.deleteById(id);
    }

    /**
     * Search for the amortizationEntry corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AmortizationEntryDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AmortizationEntries for query {}", query);
        return amortizationEntrySearchRepository.search(queryStringQuery(query), pageable).map(amortizationEntryMapper::toDto);
    }
}
