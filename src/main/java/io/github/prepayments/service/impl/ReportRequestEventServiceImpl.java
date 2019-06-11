package io.github.prepayments.service.impl;

import io.github.prepayments.service.ReportRequestEventService;
import io.github.prepayments.domain.ReportRequestEvent;
import io.github.prepayments.repository.ReportRequestEventRepository;
import io.github.prepayments.repository.search.ReportRequestEventSearchRepository;
import io.github.prepayments.service.dto.ReportRequestEventDTO;
import io.github.prepayments.service.mapper.ReportRequestEventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ReportRequestEvent}.
 */
@Service
@Transactional
public class ReportRequestEventServiceImpl implements ReportRequestEventService {

    private final Logger log = LoggerFactory.getLogger(ReportRequestEventServiceImpl.class);

    private final ReportRequestEventRepository reportRequestEventRepository;

    private final ReportRequestEventMapper reportRequestEventMapper;

    private final ReportRequestEventSearchRepository reportRequestEventSearchRepository;

    public ReportRequestEventServiceImpl(ReportRequestEventRepository reportRequestEventRepository, ReportRequestEventMapper reportRequestEventMapper, ReportRequestEventSearchRepository reportRequestEventSearchRepository) {
        this.reportRequestEventRepository = reportRequestEventRepository;
        this.reportRequestEventMapper = reportRequestEventMapper;
        this.reportRequestEventSearchRepository = reportRequestEventSearchRepository;
    }

    /**
     * Save a reportRequestEvent.
     *
     * @param reportRequestEventDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ReportRequestEventDTO save(ReportRequestEventDTO reportRequestEventDTO) {
        log.debug("Request to save ReportRequestEvent : {}", reportRequestEventDTO);
        ReportRequestEvent reportRequestEvent = reportRequestEventMapper.toEntity(reportRequestEventDTO);
        reportRequestEvent = reportRequestEventRepository.save(reportRequestEvent);
        ReportRequestEventDTO result = reportRequestEventMapper.toDto(reportRequestEvent);
        reportRequestEventSearchRepository.save(reportRequestEvent);
        return result;
    }

    /**
     * Get all the reportRequestEvents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ReportRequestEventDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ReportRequestEvents");
        return reportRequestEventRepository.findAll(pageable)
            .map(reportRequestEventMapper::toDto);
    }


    /**
     * Get one reportRequestEvent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ReportRequestEventDTO> findOne(Long id) {
        log.debug("Request to get ReportRequestEvent : {}", id);
        return reportRequestEventRepository.findById(id)
            .map(reportRequestEventMapper::toDto);
    }

    /**
     * Delete the reportRequestEvent by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReportRequestEvent : {}", id);
        reportRequestEventRepository.deleteById(id);
        reportRequestEventSearchRepository.deleteById(id);
    }

    /**
     * Search for the reportRequestEvent corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ReportRequestEventDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ReportRequestEvents for query {}", query);
        return reportRequestEventSearchRepository.search(queryStringQuery(query), pageable)
            .map(reportRequestEventMapper::toDto);
    }
}
