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

import io.github.prepayments.domain.TransactionAccount;
import io.github.prepayments.domain.*; // for static metamodels
import io.github.prepayments.repository.TransactionAccountRepository;
import io.github.prepayments.service.dto.TransactionAccountCriteria;
import io.github.prepayments.service.dto.TransactionAccountDTO;
import io.github.prepayments.service.mapper.TransactionAccountMapper;

/**
 * Service for executing complex queries for {@link TransactionAccount} entities in the database.
 * The main input is a {@link TransactionAccountCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TransactionAccountDTO} or a {@link Page} of {@link TransactionAccountDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TransactionAccountQueryService extends QueryService<TransactionAccount> {

    private final Logger log = LoggerFactory.getLogger(TransactionAccountQueryService.class);

    private final TransactionAccountRepository transactionAccountRepository;

    private final TransactionAccountMapper transactionAccountMapper;

    public TransactionAccountQueryService(TransactionAccountRepository transactionAccountRepository, TransactionAccountMapper transactionAccountMapper) {
        this.transactionAccountRepository = transactionAccountRepository;
        this.transactionAccountMapper = transactionAccountMapper;
    }

    /**
     * Return a {@link List} of {@link TransactionAccountDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TransactionAccountDTO> findByCriteria(TransactionAccountCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TransactionAccount> specification = createSpecification(criteria);
        return transactionAccountMapper.toDto(transactionAccountRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TransactionAccountDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TransactionAccountDTO> findByCriteria(TransactionAccountCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TransactionAccount> specification = createSpecification(criteria);
        return transactionAccountRepository.findAll(specification, page)
            .map(transactionAccountMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TransactionAccountCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TransactionAccount> specification = createSpecification(criteria);
        return transactionAccountRepository.count(specification);
    }

    /**
     * Function to convert TransactionAccountCriteria to a {@link Specification}.
     */
    private Specification<TransactionAccount> createSpecification(TransactionAccountCriteria criteria) {
        Specification<TransactionAccount> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TransactionAccount_.id));
            }
            if (criteria.getAccountName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountName(), TransactionAccount_.accountName));
            }
            if (criteria.getAccountNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountNumber(), TransactionAccount_.accountNumber));
            }
            if (criteria.getAccountBalance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAccountBalance(), TransactionAccount_.accountBalance));
            }
            if (criteria.getOpeningDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOpeningDate(), TransactionAccount_.openingDate));
            }
            if (criteria.getAccountOpeningDateBalance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAccountOpeningDateBalance(), TransactionAccount_.accountOpeningDateBalance));
            }
        }
        return specification;
    }
}
