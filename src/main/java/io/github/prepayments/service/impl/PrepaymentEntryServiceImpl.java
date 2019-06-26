package io.github.prepayments.service.impl;

import io.github.prepayments.domain.PrepaymentEntry;
import io.github.prepayments.repository.PrepaymentEntryRepository;
import io.github.prepayments.repository.search.PrepaymentEntrySearchRepository;
import io.github.prepayments.service.PrepaymentEntryService;
import io.github.prepayments.service.dto.PrepaymentEntryDTO;
import io.github.prepayments.service.mapper.PrepaymentEntryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link PrepaymentEntry}.
 */
@Service
@Transactional
public class PrepaymentEntryServiceImpl implements PrepaymentEntryService {

    private final Logger log = LoggerFactory.getLogger(PrepaymentEntryServiceImpl.class);

    private final PrepaymentEntryRepository prepaymentEntryRepository;

    private final PrepaymentEntryMapper prepaymentEntryMapper;

    private final PrepaymentEntrySearchRepository prepaymentEntrySearchRepository;

    public PrepaymentEntryServiceImpl(PrepaymentEntryRepository prepaymentEntryRepository, PrepaymentEntryMapper prepaymentEntryMapper,
                                      PrepaymentEntrySearchRepository prepaymentEntrySearchRepository) {
        this.prepaymentEntryRepository = prepaymentEntryRepository;
        this.prepaymentEntryMapper = prepaymentEntryMapper;
        this.prepaymentEntrySearchRepository = prepaymentEntrySearchRepository;
    }

    /**
     * Save a prepaymentEntry.
     *
     * @param prepaymentEntryDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PrepaymentEntryDTO save(PrepaymentEntryDTO prepaymentEntryDTO) {
        log.debug("Request to save PrepaymentEntry : {}", prepaymentEntryDTO);
        PrepaymentEntry prepaymentEntry = prepaymentEntryMapper.toEntity(prepaymentEntryDTO);
        prepaymentEntry = prepaymentEntryRepository.save(prepaymentEntry);
        PrepaymentEntryDTO result = prepaymentEntryMapper.toDto(prepaymentEntry);
        prepaymentEntrySearchRepository.save(prepaymentEntry);
        return result;
    }

    /**
     * Get all the prepaymentEntries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PrepaymentEntryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PrepaymentEntries");
        return prepaymentEntryRepository.findAll(pageable).map(prepaymentEntryMapper::toDto);
    }


    /**
     * Get one prepaymentEntry by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PrepaymentEntryDTO> findOne(Long id) {
        log.debug("Request to get PrepaymentEntry : {}", id);
        return prepaymentEntryRepository.findById(id).map(prepaymentEntryMapper::toDto);
    }

    /**
     * Delete the prepaymentEntry by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PrepaymentEntry : {}", id);
        prepaymentEntryRepository.deleteById(id);
        prepaymentEntrySearchRepository.deleteById(id);
    }

    /**
     * Search for the prepaymentEntry corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PrepaymentEntryDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PrepaymentEntries for query {}", query);
        return prepaymentEntrySearchRepository.search(queryStringQuery(query), pageable).map(prepaymentEntryMapper::toDto);
    }
}
