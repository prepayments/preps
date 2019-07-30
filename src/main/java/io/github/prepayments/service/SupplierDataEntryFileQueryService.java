package io.github.prepayments.service;

import io.github.jhipster.service.QueryService;
import io.github.prepayments.domain.SupplierDataEntryFile;
import io.github.prepayments.domain.SupplierDataEntryFile_;
import io.github.prepayments.repository.SupplierDataEntryFileRepository;
import io.github.prepayments.service.dto.SupplierDataEntryFileCriteria;
import io.github.prepayments.service.dto.SupplierDataEntryFileDTO;
import io.github.prepayments.service.mapper.SupplierDataEntryFileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for executing complex queries for {@link SupplierDataEntryFile} entities in the database.
 * The main input is a {@link SupplierDataEntryFileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SupplierDataEntryFileDTO} or a {@link Page} of {@link SupplierDataEntryFileDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SupplierDataEntryFileQueryService extends QueryService<SupplierDataEntryFile> {

    private final Logger log = LoggerFactory.getLogger(SupplierDataEntryFileQueryService.class);

    private final SupplierDataEntryFileRepository supplierDataEntryFileRepository;

    private final SupplierDataEntryFileMapper supplierDataEntryFileMapper;

    public SupplierDataEntryFileQueryService(SupplierDataEntryFileRepository supplierDataEntryFileRepository, SupplierDataEntryFileMapper supplierDataEntryFileMapper) {
        this.supplierDataEntryFileRepository = supplierDataEntryFileRepository;
        this.supplierDataEntryFileMapper = supplierDataEntryFileMapper;
    }

    /**
     * Return a {@link List} of {@link SupplierDataEntryFileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SupplierDataEntryFileDTO> findByCriteria(SupplierDataEntryFileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SupplierDataEntryFile> specification = createSpecification(criteria);
        return supplierDataEntryFileMapper.toDto(supplierDataEntryFileRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SupplierDataEntryFileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SupplierDataEntryFileDTO> findByCriteria(SupplierDataEntryFileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SupplierDataEntryFile> specification = createSpecification(criteria);
        return supplierDataEntryFileRepository.findAll(specification, page).map(supplierDataEntryFileMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SupplierDataEntryFileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SupplierDataEntryFile> specification = createSpecification(criteria);
        return supplierDataEntryFileRepository.count(specification);
    }

    /**
     * Function to convert SupplierDataEntryFileCriteria to a {@link Specification}.
     */
    private Specification<SupplierDataEntryFile> createSpecification(SupplierDataEntryFileCriteria criteria) {
        Specification<SupplierDataEntryFile> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SupplierDataEntryFile_.id));
            }
            if (criteria.getPeriodFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPeriodFrom(), SupplierDataEntryFile_.periodFrom));
            }
            if (criteria.getPeriodTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPeriodTo(), SupplierDataEntryFile_.periodTo));
            }
            if (criteria.getUploadSuccessful() != null) {
                specification = specification.and(buildSpecification(criteria.getUploadSuccessful(), SupplierDataEntryFile_.uploadSuccessful));
            }
            if (criteria.getUploadProcessed() != null) {
                specification = specification.and(buildSpecification(criteria.getUploadProcessed(), SupplierDataEntryFile_.uploadProcessed));
            }
            if (criteria.getEntriesCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEntriesCount(), SupplierDataEntryFile_.entriesCount));
            }
            if (criteria.getFileToken() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileToken(), SupplierDataEntryFile_.fileToken));
            }
        }
        return specification;
    }
}
