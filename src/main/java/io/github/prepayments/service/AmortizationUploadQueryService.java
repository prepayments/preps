package io.github.prepayments.service;

import io.github.jhipster.service.QueryService;
import io.github.prepayments.domain.AmortizationUpload;
import io.github.prepayments.domain.AmortizationUpload_;
import io.github.prepayments.repository.AmortizationUploadRepository;
import io.github.prepayments.repository.search.AmortizationUploadSearchRepository;
import io.github.prepayments.service.dto.AmortizationUploadCriteria;
import io.github.prepayments.service.dto.AmortizationUploadDTO;
import io.github.prepayments.service.mapper.AmortizationUploadMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for executing complex queries for {@link AmortizationUpload} entities in the database. The main input is a {@link AmortizationUploadCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply. It returns a {@link List} of {@link AmortizationUploadDTO} or a {@link Page} of {@link AmortizationUploadDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AmortizationUploadQueryService extends QueryService<AmortizationUpload> {

    private final Logger log = LoggerFactory.getLogger(AmortizationUploadQueryService.class);

    private final AmortizationUploadRepository amortizationUploadRepository;

    private final AmortizationUploadMapper amortizationUploadMapper;

    private final AmortizationUploadSearchRepository amortizationUploadSearchRepository;

    public AmortizationUploadQueryService(AmortizationUploadRepository amortizationUploadRepository, AmortizationUploadMapper amortizationUploadMapper,
                                          AmortizationUploadSearchRepository amortizationUploadSearchRepository) {
        this.amortizationUploadRepository = amortizationUploadRepository;
        this.amortizationUploadMapper = amortizationUploadMapper;
        this.amortizationUploadSearchRepository = amortizationUploadSearchRepository;
    }

    /**
     * Return a {@link List} of {@link AmortizationUploadDTO} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AmortizationUploadDTO> findByCriteria(AmortizationUploadCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AmortizationUpload> specification = createSpecification(criteria);
        return amortizationUploadMapper.toDto(amortizationUploadRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AmortizationUploadDTO} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AmortizationUploadDTO> findByCriteria(AmortizationUploadCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AmortizationUpload> specification = createSpecification(criteria);
        return amortizationUploadRepository.findAll(specification, page).map(amortizationUploadMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AmortizationUploadCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AmortizationUpload> specification = createSpecification(criteria);
        return amortizationUploadRepository.count(specification);
    }

    /**
     * Function to convert AmortizationUploadCriteria to a {@link Specification}.
     */
    private Specification<AmortizationUpload> createSpecification(AmortizationUploadCriteria criteria) {
        Specification<AmortizationUpload> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), AmortizationUpload_.id));
            }
            if (criteria.getAccountName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountName(), AmortizationUpload_.accountName));
            }
            if (criteria.getParticulars() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParticulars(), AmortizationUpload_.particulars));
            }
            if (criteria.getServiceOutletCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getServiceOutletCode(), AmortizationUpload_.serviceOutletCode));
            }
            if (criteria.getPrepaymentAccountNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrepaymentAccountNumber(), AmortizationUpload_.prepaymentAccountNumber));
            }
            if (criteria.getExpenseAccountNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExpenseAccountNumber(), AmortizationUpload_.expenseAccountNumber));
            }
            if (criteria.getPrepaymentTransactionId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrepaymentTransactionId(), AmortizationUpload_.prepaymentTransactionId));
            }
            if (criteria.getPrepaymentTransactionDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrepaymentTransactionDate(), AmortizationUpload_.prepaymentTransactionDate));
            }
            if (criteria.getPrepaymentTransactionAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrepaymentTransactionAmount(), AmortizationUpload_.prepaymentTransactionAmount));
            }
            if (criteria.getAmortizationAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmortizationAmount(), AmortizationUpload_.amortizationAmount));
            }
            if (criteria.getNumberOfAmortizations() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberOfAmortizations(), AmortizationUpload_.numberOfAmortizations));
            }
            if (criteria.getFirstAmortizationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFirstAmortizationDate(), AmortizationUpload_.firstAmortizationDate));
            }
        }
        return specification;
    }
}
