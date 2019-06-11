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

import io.github.prepayments.domain.AmortizationDataEntryFile;
import io.github.prepayments.domain.*; // for static metamodels
import io.github.prepayments.repository.AmortizationDataEntryFileRepository;
import io.github.prepayments.service.dto.AmortizationDataEntryFileCriteria;
import io.github.prepayments.service.dto.AmortizationDataEntryFileDTO;
import io.github.prepayments.service.mapper.AmortizationDataEntryFileMapper;

/**
 * Service for executing complex queries for {@link AmortizationDataEntryFile} entities in the database.
 * The main input is a {@link AmortizationDataEntryFileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AmortizationDataEntryFileDTO} or a {@link Page} of {@link AmortizationDataEntryFileDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AmortizationDataEntryFileQueryService extends QueryService<AmortizationDataEntryFile> {

    private final Logger log = LoggerFactory.getLogger(AmortizationDataEntryFileQueryService.class);

    private final AmortizationDataEntryFileRepository amortizationDataEntryFileRepository;

    private final AmortizationDataEntryFileMapper amortizationDataEntryFileMapper;

    public AmortizationDataEntryFileQueryService(AmortizationDataEntryFileRepository amortizationDataEntryFileRepository, AmortizationDataEntryFileMapper amortizationDataEntryFileMapper) {
        this.amortizationDataEntryFileRepository = amortizationDataEntryFileRepository;
        this.amortizationDataEntryFileMapper = amortizationDataEntryFileMapper;
    }

    /**
     * Return a {@link List} of {@link AmortizationDataEntryFileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AmortizationDataEntryFileDTO> findByCriteria(AmortizationDataEntryFileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AmortizationDataEntryFile> specification = createSpecification(criteria);
        return amortizationDataEntryFileMapper.toDto(amortizationDataEntryFileRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AmortizationDataEntryFileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AmortizationDataEntryFileDTO> findByCriteria(AmortizationDataEntryFileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AmortizationDataEntryFile> specification = createSpecification(criteria);
        return amortizationDataEntryFileRepository.findAll(specification, page)
            .map(amortizationDataEntryFileMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AmortizationDataEntryFileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AmortizationDataEntryFile> specification = createSpecification(criteria);
        return amortizationDataEntryFileRepository.count(specification);
    }

    /**
     * Function to convert AmortizationDataEntryFileCriteria to a {@link Specification}.
     */
    private Specification<AmortizationDataEntryFile> createSpecification(AmortizationDataEntryFileCriteria criteria) {
        Specification<AmortizationDataEntryFile> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), AmortizationDataEntryFile_.id));
            }
            if (criteria.getPeriodFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPeriodFrom(), AmortizationDataEntryFile_.periodFrom));
            }
            if (criteria.getPeriodTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPeriodTo(), AmortizationDataEntryFile_.periodTo));
            }
            if (criteria.getUploadSuccessful() != null) {
                specification = specification.and(buildSpecification(criteria.getUploadSuccessful(), AmortizationDataEntryFile_.uploadSuccessful));
            }
            if (criteria.getUploadProcessed() != null) {
                specification = specification.and(buildSpecification(criteria.getUploadProcessed(), AmortizationDataEntryFile_.uploadProcessed));
            }
        }
        return specification;
    }
}
