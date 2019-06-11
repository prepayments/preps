package io.github.prepayments.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import io.github.prepayments.domain.ReportRequestEvent;
import io.github.prepayments.domain.*; // for static metamodels
import io.github.prepayments.repository.ReportRequestEventRepository;
import io.github.prepayments.service.dto.ReportRequestEventCriteria;
import io.github.prepayments.service.dto.ReportRequestEventDTO;
import io.github.prepayments.service.mapper.ReportRequestEventMapper;

/**
 * Service for executing complex queries for {@link ReportRequestEvent} entities in the database.
 * The main input is a {@link ReportRequestEventCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ReportRequestEventDTO} or a {@link Page} of {@link ReportRequestEventDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReportRequestEventQueryService extends QueryService<ReportRequestEvent> {

    private final Logger log = LoggerFactory.getLogger(ReportRequestEventQueryService.class);

    private final ReportRequestEventRepository reportRequestEventRepository;

    private final ReportRequestEventMapper reportRequestEventMapper;

    public ReportRequestEventQueryService(ReportRequestEventRepository reportRequestEventRepository, ReportRequestEventMapper reportRequestEventMapper) {
        this.reportRequestEventRepository = reportRequestEventRepository;
        this.reportRequestEventMapper = reportRequestEventMapper;
    }

    /**
     * Return a {@link List} of {@link ReportRequestEventDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ReportRequestEventDTO> findByCriteria(ReportRequestEventCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ReportRequestEvent> specification = createSpecification(criteria);
        return reportRequestEventMapper.toDto(reportRequestEventRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ReportRequestEventDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ReportRequestEventDTO> findByCriteria(ReportRequestEventCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ReportRequestEvent> specification = createSpecification(criteria);
        return reportRequestEventRepository.findAll(specification, page)
            .map(reportRequestEventMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ReportRequestEventCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ReportRequestEvent> specification = createSpecification(criteria);
        return reportRequestEventRepository.count(specification);
    }

    /**
     * Function to convert ReportRequestEventCriteria to a {@link Specification}.
     */
    private Specification<ReportRequestEvent> createSpecification(ReportRequestEventCriteria criteria) {
        Specification<ReportRequestEvent> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ReportRequestEvent_.id));
            }
            if (criteria.getReportRequestDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReportRequestDate(), ReportRequestEvent_.reportRequestDate));
            }
            if (criteria.getRequestedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRequestedBy(), ReportRequestEvent_.requestedBy));
            }
            if (criteria.getReportTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getReportTypeId(),
                    root -> root.join(ReportRequestEvent_.reportType, JoinType.LEFT).get(ReportType_.id)));
            }
        }
        return specification;
    }
}
