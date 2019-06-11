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

import io.github.prepayments.domain.RegisteredSupplier;
import io.github.prepayments.domain.*; // for static metamodels
import io.github.prepayments.repository.RegisteredSupplierRepository;
import io.github.prepayments.repository.search.RegisteredSupplierSearchRepository;
import io.github.prepayments.service.dto.RegisteredSupplierCriteria;
import io.github.prepayments.service.dto.RegisteredSupplierDTO;
import io.github.prepayments.service.mapper.RegisteredSupplierMapper;

/**
 * Service for executing complex queries for {@link RegisteredSupplier} entities in the database.
 * The main input is a {@link RegisteredSupplierCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RegisteredSupplierDTO} or a {@link Page} of {@link RegisteredSupplierDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RegisteredSupplierQueryService extends QueryService<RegisteredSupplier> {

    private final Logger log = LoggerFactory.getLogger(RegisteredSupplierQueryService.class);

    private final RegisteredSupplierRepository registeredSupplierRepository;

    private final RegisteredSupplierMapper registeredSupplierMapper;

    private final RegisteredSupplierSearchRepository registeredSupplierSearchRepository;

    public RegisteredSupplierQueryService(RegisteredSupplierRepository registeredSupplierRepository, RegisteredSupplierMapper registeredSupplierMapper, RegisteredSupplierSearchRepository registeredSupplierSearchRepository) {
        this.registeredSupplierRepository = registeredSupplierRepository;
        this.registeredSupplierMapper = registeredSupplierMapper;
        this.registeredSupplierSearchRepository = registeredSupplierSearchRepository;
    }

    /**
     * Return a {@link List} of {@link RegisteredSupplierDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RegisteredSupplierDTO> findByCriteria(RegisteredSupplierCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RegisteredSupplier> specification = createSpecification(criteria);
        return registeredSupplierMapper.toDto(registeredSupplierRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RegisteredSupplierDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RegisteredSupplierDTO> findByCriteria(RegisteredSupplierCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RegisteredSupplier> specification = createSpecification(criteria);
        return registeredSupplierRepository.findAll(specification, page)
            .map(registeredSupplierMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RegisteredSupplierCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RegisteredSupplier> specification = createSpecification(criteria);
        return registeredSupplierRepository.count(specification);
    }

    /**
     * Function to convert RegisteredSupplierCriteria to a {@link Specification}.
     */
    private Specification<RegisteredSupplier> createSpecification(RegisteredSupplierCriteria criteria) {
        Specification<RegisteredSupplier> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), RegisteredSupplier_.id));
            }
            if (criteria.getSupplierName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSupplierName(), RegisteredSupplier_.supplierName));
            }
            if (criteria.getSupplierAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSupplierAddress(), RegisteredSupplier_.supplierAddress));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNumber(), RegisteredSupplier_.phoneNumber));
            }
            if (criteria.getSupplierEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSupplierEmail(), RegisteredSupplier_.supplierEmail));
            }
            if (criteria.getBankAccountName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankAccountName(), RegisteredSupplier_.bankAccountName));
            }
            if (criteria.getBankAccountNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankAccountNumber(), RegisteredSupplier_.bankAccountNumber));
            }
            if (criteria.getSupplierBankName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSupplierBankName(), RegisteredSupplier_.supplierBankName));
            }
            if (criteria.getSupplierBankBranch() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSupplierBankBranch(), RegisteredSupplier_.supplierBankBranch));
            }
            if (criteria.getBankSwiftCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankSwiftCode(), RegisteredSupplier_.bankSwiftCode));
            }
            if (criteria.getBankPhysicalAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankPhysicalAddress(), RegisteredSupplier_.bankPhysicalAddress));
            }
            if (criteria.getLocallyDomiciled() != null) {
                specification = specification.and(buildSpecification(criteria.getLocallyDomiciled(), RegisteredSupplier_.locallyDomiciled));
            }
            if (criteria.getTaxAuthorityPIN() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTaxAuthorityPIN(), RegisteredSupplier_.taxAuthorityPIN));
            }
        }
        return specification;
    }
}
