package io.github.prepayments.service.impl;

import io.github.prepayments.service.UpdatedAmortizationEntryService;
import io.github.prepayments.domain.UpdatedAmortizationEntry;
import io.github.prepayments.repository.UpdatedAmortizationEntryRepository;
import io.github.prepayments.repository.search.UpdatedAmortizationEntrySearchRepository;
import io.github.prepayments.service.dto.UpdatedAmortizationEntryDTO;
import io.github.prepayments.service.mapper.UpdatedAmortizationEntryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link UpdatedAmortizationEntry}.
 */
@Service
@Transactional
public class UpdatedAmortizationEntryServiceImpl implements UpdatedAmortizationEntryService {

    private final Logger log = LoggerFactory.getLogger(UpdatedAmortizationEntryServiceImpl.class);

    private final UpdatedAmortizationEntryRepository updatedAmortizationEntryRepository;

    private final UpdatedAmortizationEntryMapper updatedAmortizationEntryMapper;

    private final UpdatedAmortizationEntrySearchRepository updatedAmortizationEntrySearchRepository;

    public UpdatedAmortizationEntryServiceImpl(UpdatedAmortizationEntryRepository updatedAmortizationEntryRepository, UpdatedAmortizationEntryMapper updatedAmortizationEntryMapper, UpdatedAmortizationEntrySearchRepository updatedAmortizationEntrySearchRepository) {
        this.updatedAmortizationEntryRepository = updatedAmortizationEntryRepository;
        this.updatedAmortizationEntryMapper = updatedAmortizationEntryMapper;
        this.updatedAmortizationEntrySearchRepository = updatedAmortizationEntrySearchRepository;
    }

    /**
     * Save a updatedAmortizationEntry.
     *
     * @param updatedAmortizationEntryDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UpdatedAmortizationEntryDTO save(UpdatedAmortizationEntryDTO updatedAmortizationEntryDTO) {
        log.debug("Request to save UpdatedAmortizationEntry : {}", updatedAmortizationEntryDTO);
        UpdatedAmortizationEntry updatedAmortizationEntry = updatedAmortizationEntryMapper.toEntity(updatedAmortizationEntryDTO);
        updatedAmortizationEntry = updatedAmortizationEntryRepository.save(updatedAmortizationEntry);
        UpdatedAmortizationEntryDTO result = updatedAmortizationEntryMapper.toDto(updatedAmortizationEntry);
        updatedAmortizationEntrySearchRepository.save(updatedAmortizationEntry);
        return result;
    }

    /**
     * Get all the updatedAmortizationEntries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UpdatedAmortizationEntryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UpdatedAmortizationEntries");
        return updatedAmortizationEntryRepository.findAll(pageable)
            .map(updatedAmortizationEntryMapper::toDto);
    }


    /**
     * Get one updatedAmortizationEntry by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UpdatedAmortizationEntryDTO> findOne(Long id) {
        log.debug("Request to get UpdatedAmortizationEntry : {}", id);
        return updatedAmortizationEntryRepository.findById(id)
            .map(updatedAmortizationEntryMapper::toDto);
    }

    /**
     * Delete the updatedAmortizationEntry by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UpdatedAmortizationEntry : {}", id);
        updatedAmortizationEntryRepository.deleteById(id);
        updatedAmortizationEntrySearchRepository.deleteById(id);
    }

    /**
     * Search for the updatedAmortizationEntry corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UpdatedAmortizationEntryDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UpdatedAmortizationEntries for query {}", query);
        return updatedAmortizationEntrySearchRepository.search(queryStringQuery(query), pageable)
            .map(updatedAmortizationEntryMapper::toDto);
    }
}
