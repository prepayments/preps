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

import io.github.prepayments.domain.UpdatedAmortizationEntry;
import io.github.prepayments.domain.*; // for static metamodels
import io.github.prepayments.repository.UpdatedAmortizationEntryRepository;
import io.github.prepayments.repository.search.UpdatedAmortizationEntrySearchRepository;
import io.github.prepayments.service.dto.UpdatedAmortizationEntryCriteria;
import io.github.prepayments.service.dto.UpdatedAmortizationEntryDTO;
import io.github.prepayments.service.mapper.UpdatedAmortizationEntryMapper;

/**
 * Service for executing complex queries for {@link UpdatedAmortizationEntry} entities in the database.
 * The main input is a {@link UpdatedAmortizationEntryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UpdatedAmortizationEntryDTO} or a {@link Page} of {@link UpdatedAmortizationEntryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UpdatedAmortizationEntryQueryService extends QueryService<UpdatedAmortizationEntry> {

    private final Logger log = LoggerFactory.getLogger(UpdatedAmortizationEntryQueryService.class);

    private final UpdatedAmortizationEntryRepository updatedAmortizationEntryRepository;

    private final UpdatedAmortizationEntryMapper updatedAmortizationEntryMapper;

    private final UpdatedAmortizationEntrySearchRepository updatedAmortizationEntrySearchRepository;

    public UpdatedAmortizationEntryQueryService(UpdatedAmortizationEntryRepository updatedAmortizationEntryRepository, UpdatedAmortizationEntryMapper updatedAmortizationEntryMapper, UpdatedAmortizationEntrySearchRepository updatedAmortizationEntrySearchRepository) {
        this.updatedAmortizationEntryRepository = updatedAmortizationEntryRepository;
        this.updatedAmortizationEntryMapper = updatedAmortizationEntryMapper;
        this.updatedAmortizationEntrySearchRepository = updatedAmortizationEntrySearchRepository;
    }

    /**
     * Return a {@link List} of {@link UpdatedAmortizationEntryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UpdatedAmortizationEntryDTO> findByCriteria(UpdatedAmortizationEntryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UpdatedAmortizationEntry> specification = createSpecification(criteria);
        return updatedAmortizationEntryMapper.toDto(updatedAmortizationEntryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UpdatedAmortizationEntryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UpdatedAmortizationEntryDTO> findByCriteria(UpdatedAmortizationEntryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UpdatedAmortizationEntry> specification = createSpecification(criteria);
        return updatedAmortizationEntryRepository.findAll(specification, page)
            .map(updatedAmortizationEntryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UpdatedAmortizationEntryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UpdatedAmortizationEntry> specification = createSpecification(criteria);
        return updatedAmortizationEntryRepository.count(specification);
    }

    /**
     * Function to convert UpdatedAmortizationEntryCriteria to a {@link Specification}.
     */
    private Specification<UpdatedAmortizationEntry> createSpecification(UpdatedAmortizationEntryCriteria criteria) {
        Specification<UpdatedAmortizationEntry> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), UpdatedAmortizationEntry_.id));
            }
            if (criteria.getAmortizationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmortizationDate(), UpdatedAmortizationEntry_.amortizationDate));
            }
            if (criteria.getAmortizationAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmortizationAmount(), UpdatedAmortizationEntry_.amortizationAmount));
            }
            if (criteria.getParticulars() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParticulars(), UpdatedAmortizationEntry_.particulars));
            }
            if (criteria.getPrepaymentServiceOutlet() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrepaymentServiceOutlet(), UpdatedAmortizationEntry_.prepaymentServiceOutlet));
            }
            if (criteria.getPrepaymentAccountNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrepaymentAccountNumber(), UpdatedAmortizationEntry_.prepaymentAccountNumber));
            }
            if (criteria.getAmortizationServiceOutlet() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAmortizationServiceOutlet(), UpdatedAmortizationEntry_.amortizationServiceOutlet));
            }
            if (criteria.getAmortizationAccountNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAmortizationAccountNumber(), UpdatedAmortizationEntry_.amortizationAccountNumber));
            }
            if (criteria.getOriginatingFileToken() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOriginatingFileToken(), UpdatedAmortizationEntry_.originatingFileToken));
            }
            if (criteria.getAmortizationTag() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAmortizationTag(), UpdatedAmortizationEntry_.amortizationTag));
            }
            if (criteria.getOrphaned() != null) {
                specification = specification.and(buildSpecification(criteria.getOrphaned(), UpdatedAmortizationEntry_.orphaned));
            }
            if (criteria.getDateOfUpdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOfUpdate(), UpdatedAmortizationEntry_.dateOfUpdate));
            }
            if (criteria.getReasonForUpdate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReasonForUpdate(), UpdatedAmortizationEntry_.reasonForUpdate));
            }
            if (criteria.getPrepaymentEntryId() != null) {
                specification = specification.and(buildSpecification(criteria.getPrepaymentEntryId(),
                    root -> root.join(UpdatedAmortizationEntry_.prepaymentEntry, JoinType.LEFT).get(PrepaymentEntry_.id)));
            }
        }
        return specification;
    }
}
