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

import io.github.prepayments.domain.ReportType;
import io.github.prepayments.domain.*; // for static metamodels
import io.github.prepayments.repository.ReportTypeRepository;
import io.github.prepayments.service.dto.ReportTypeCriteria;
import io.github.prepayments.service.dto.ReportTypeDTO;
import io.github.prepayments.service.mapper.ReportTypeMapper;

/**
 * Service for executing complex queries for {@link ReportType} entities in the database.
 * The main input is a {@link ReportTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ReportTypeDTO} or a {@link Page} of {@link ReportTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReportTypeQueryService extends QueryService<ReportType> {

    private final Logger log = LoggerFactory.getLogger(ReportTypeQueryService.class);

    private final ReportTypeRepository reportTypeRepository;

    private final ReportTypeMapper reportTypeMapper;

    public ReportTypeQueryService(ReportTypeRepository reportTypeRepository, ReportTypeMapper reportTypeMapper) {
        this.reportTypeRepository = reportTypeRepository;
        this.reportTypeMapper = reportTypeMapper;
    }

    /**
     * Return a {@link List} of {@link ReportTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ReportTypeDTO> findByCriteria(ReportTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ReportType> specification = createSpecification(criteria);
        return reportTypeMapper.toDto(reportTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ReportTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ReportTypeDTO> findByCriteria(ReportTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ReportType> specification = createSpecification(criteria);
        return reportTypeRepository.findAll(specification, page)
            .map(reportTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ReportTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ReportType> specification = createSpecification(criteria);
        return reportTypeRepository.count(specification);
    }

    /**
     * Function to convert ReportTypeCriteria to a {@link Specification}.
     */
    private Specification<ReportType> createSpecification(ReportTypeCriteria criteria) {
        Specification<ReportType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ReportType_.id));
            }
            if (criteria.getReportModelName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReportModelName(), ReportType_.reportModelName));
            }
            if (criteria.getReportMediumType() != null) {
                specification = specification.and(buildSpecification(criteria.getReportMediumType(), ReportType_.reportMediumType));
            }
            if (criteria.getReportPassword() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReportPassword(), ReportType_.reportPassword));
            }
        }
        return specification;
    }
}
