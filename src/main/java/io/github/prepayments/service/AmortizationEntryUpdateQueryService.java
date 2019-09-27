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

import io.github.prepayments.domain.AmortizationEntryUpdate;
import io.github.prepayments.domain.*; // for static metamodels
import io.github.prepayments.repository.AmortizationEntryUpdateRepository;
import io.github.prepayments.repository.search.AmortizationEntryUpdateSearchRepository;
import io.github.prepayments.service.dto.AmortizationEntryUpdateCriteria;
import io.github.prepayments.service.dto.AmortizationEntryUpdateDTO;
import io.github.prepayments.service.mapper.AmortizationEntryUpdateMapper;

/**
 * Service for executing complex queries for {@link AmortizationEntryUpdate} entities in the database.
 * The main input is a {@link AmortizationEntryUpdateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AmortizationEntryUpdateDTO} or a {@link Page} of {@link AmortizationEntryUpdateDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AmortizationEntryUpdateQueryService extends QueryService<AmortizationEntryUpdate> {

    private final Logger log = LoggerFactory.getLogger(AmortizationEntryUpdateQueryService.class);

    private final AmortizationEntryUpdateRepository amortizationEntryUpdateRepository;

    private final AmortizationEntryUpdateMapper amortizationEntryUpdateMapper;

    private final AmortizationEntryUpdateSearchRepository amortizationEntryUpdateSearchRepository;

    public AmortizationEntryUpdateQueryService(AmortizationEntryUpdateRepository amortizationEntryUpdateRepository, AmortizationEntryUpdateMapper amortizationEntryUpdateMapper, AmortizationEntryUpdateSearchRepository amortizationEntryUpdateSearchRepository) {
        this.amortizationEntryUpdateRepository = amortizationEntryUpdateRepository;
        this.amortizationEntryUpdateMapper = amortizationEntryUpdateMapper;
        this.amortizationEntryUpdateSearchRepository = amortizationEntryUpdateSearchRepository;
    }

    /**
     * Return a {@link List} of {@link AmortizationEntryUpdateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AmortizationEntryUpdateDTO> findByCriteria(AmortizationEntryUpdateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AmortizationEntryUpdate> specification = createSpecification(criteria);
        return amortizationEntryUpdateMapper.toDto(amortizationEntryUpdateRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AmortizationEntryUpdateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AmortizationEntryUpdateDTO> findByCriteria(AmortizationEntryUpdateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AmortizationEntryUpdate> specification = createSpecification(criteria);
        return amortizationEntryUpdateRepository.findAll(specification, page)
            .map(amortizationEntryUpdateMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AmortizationEntryUpdateCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AmortizationEntryUpdate> specification = createSpecification(criteria);
        return amortizationEntryUpdateRepository.count(specification);
    }

    /**
     * Function to convert AmortizationEntryUpdateCriteria to a {@link Specification}.
     */
    private Specification<AmortizationEntryUpdate> createSpecification(AmortizationEntryUpdateCriteria criteria) {
        Specification<AmortizationEntryUpdate> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), AmortizationEntryUpdate_.id));
            }
            if (criteria.getAmortizationEntryId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmortizationEntryId(), AmortizationEntryUpdate_.amortizationEntryId));
            }
            if (criteria.getAmortizationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmortizationDate(), AmortizationEntryUpdate_.amortizationDate));
            }
            if (criteria.getAmortizationAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmortizationAmount(), AmortizationEntryUpdate_.amortizationAmount));
            }
            if (criteria.getParticulars() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParticulars(), AmortizationEntryUpdate_.particulars));
            }
            if (criteria.getPrepaymentServiceOutlet() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrepaymentServiceOutlet(), AmortizationEntryUpdate_.prepaymentServiceOutlet));
            }
            if (criteria.getPrepaymentAccountNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrepaymentAccountNumber(), AmortizationEntryUpdate_.prepaymentAccountNumber));
            }
            if (criteria.getAmortizationServiceOutlet() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAmortizationServiceOutlet(), AmortizationEntryUpdate_.amortizationServiceOutlet));
            }
            if (criteria.getAmortizationAccountNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAmortizationAccountNumber(), AmortizationEntryUpdate_.amortizationAccountNumber));
            }
            if (criteria.getPrepaymentEntryId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrepaymentEntryId(), AmortizationEntryUpdate_.prepaymentEntryId));
            }
            if (criteria.getOriginatingFileToken() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOriginatingFileToken(), AmortizationEntryUpdate_.originatingFileToken));
            }
            if (criteria.getAmortizationTag() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAmortizationTag(), AmortizationEntryUpdate_.amortizationTag));
            }
            if (criteria.getOrphaned() != null) {
                specification = specification.and(buildSpecification(criteria.getOrphaned(), AmortizationEntryUpdate_.orphaned));
            }
        }
        return specification;
    }
}
