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

import io.github.prepayments.domain.AccountingTransaction;
import io.github.prepayments.domain.*; // for static metamodels
import io.github.prepayments.repository.AccountingTransactionRepository;
import io.github.prepayments.repository.search.AccountingTransactionSearchRepository;
import io.github.prepayments.service.dto.AccountingTransactionCriteria;
import io.github.prepayments.service.dto.AccountingTransactionDTO;
import io.github.prepayments.service.mapper.AccountingTransactionMapper;

/**
 * Service for executing complex queries for {@link AccountingTransaction} entities in the database.
 * The main input is a {@link AccountingTransactionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AccountingTransactionDTO} or a {@link Page} of {@link AccountingTransactionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AccountingTransactionQueryService extends QueryService<AccountingTransaction> {

    private final Logger log = LoggerFactory.getLogger(AccountingTransactionQueryService.class);

    private final AccountingTransactionRepository accountingTransactionRepository;

    private final AccountingTransactionMapper accountingTransactionMapper;

    private final AccountingTransactionSearchRepository accountingTransactionSearchRepository;

    public AccountingTransactionQueryService(AccountingTransactionRepository accountingTransactionRepository, AccountingTransactionMapper accountingTransactionMapper, AccountingTransactionSearchRepository accountingTransactionSearchRepository) {
        this.accountingTransactionRepository = accountingTransactionRepository;
        this.accountingTransactionMapper = accountingTransactionMapper;
        this.accountingTransactionSearchRepository = accountingTransactionSearchRepository;
    }

    /**
     * Return a {@link List} of {@link AccountingTransactionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AccountingTransactionDTO> findByCriteria(AccountingTransactionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AccountingTransaction> specification = createSpecification(criteria);
        return accountingTransactionMapper.toDto(accountingTransactionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AccountingTransactionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AccountingTransactionDTO> findByCriteria(AccountingTransactionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AccountingTransaction> specification = createSpecification(criteria);
        return accountingTransactionRepository.findAll(specification, page)
            .map(accountingTransactionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AccountingTransactionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AccountingTransaction> specification = createSpecification(criteria);
        return accountingTransactionRepository.count(specification);
    }

    /**
     * Function to convert AccountingTransactionCriteria to a {@link Specification}.
     */
    private Specification<AccountingTransaction> createSpecification(AccountingTransactionCriteria criteria) {
        Specification<AccountingTransaction> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), AccountingTransaction_.id));
            }
            if (criteria.getAccountName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountName(), AccountingTransaction_.accountName));
            }
            if (criteria.getAccountNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountNumber(), AccountingTransaction_.accountNumber));
            }
            if (criteria.getTransactionDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTransactionDate(), AccountingTransaction_.transactionDate));
            }
            if (criteria.getTransactionAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTransactionAmount(), AccountingTransaction_.transactionAmount));
            }
            if (criteria.getIncrementAccount() != null) {
                specification = specification.and(buildSpecification(criteria.getIncrementAccount(), AccountingTransaction_.incrementAccount));
            }
        }
        return specification;
    }
}
