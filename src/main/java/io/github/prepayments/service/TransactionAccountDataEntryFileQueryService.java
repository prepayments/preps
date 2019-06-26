package io.github.prepayments.service;

import io.github.jhipster.service.QueryService;
import io.github.prepayments.domain.TransactionAccountDataEntryFile;
import io.github.prepayments.domain.TransactionAccountDataEntryFile_;
import io.github.prepayments.repository.TransactionAccountDataEntryFileRepository;
import io.github.prepayments.service.dto.TransactionAccountDataEntryFileCriteria;
import io.github.prepayments.service.dto.TransactionAccountDataEntryFileDTO;
import io.github.prepayments.service.mapper.TransactionAccountDataEntryFileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for executing complex queries for {@link TransactionAccountDataEntryFile} entities in the database. The main input is a {@link TransactionAccountDataEntryFileCriteria} which gets converted
 * to {@link Specification}, in a way that all the filters must apply. It returns a {@link List} of {@link TransactionAccountDataEntryFileDTO} or a {@link Page} of {@link
 * TransactionAccountDataEntryFileDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TransactionAccountDataEntryFileQueryService extends QueryService<TransactionAccountDataEntryFile> {

    private final Logger log = LoggerFactory.getLogger(TransactionAccountDataEntryFileQueryService.class);

    private final TransactionAccountDataEntryFileRepository transactionAccountDataEntryFileRepository;

    private final TransactionAccountDataEntryFileMapper transactionAccountDataEntryFileMapper;

    public TransactionAccountDataEntryFileQueryService(TransactionAccountDataEntryFileRepository transactionAccountDataEntryFileRepository,
                                                       TransactionAccountDataEntryFileMapper transactionAccountDataEntryFileMapper) {
        this.transactionAccountDataEntryFileRepository = transactionAccountDataEntryFileRepository;
        this.transactionAccountDataEntryFileMapper = transactionAccountDataEntryFileMapper;
    }

    /**
     * Return a {@link List} of {@link TransactionAccountDataEntryFileDTO} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TransactionAccountDataEntryFileDTO> findByCriteria(TransactionAccountDataEntryFileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TransactionAccountDataEntryFile> specification = createSpecification(criteria);
        return transactionAccountDataEntryFileMapper.toDto(transactionAccountDataEntryFileRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TransactionAccountDataEntryFileDTO} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TransactionAccountDataEntryFileDTO> findByCriteria(TransactionAccountDataEntryFileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TransactionAccountDataEntryFile> specification = createSpecification(criteria);
        return transactionAccountDataEntryFileRepository.findAll(specification, page).map(transactionAccountDataEntryFileMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TransactionAccountDataEntryFileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TransactionAccountDataEntryFile> specification = createSpecification(criteria);
        return transactionAccountDataEntryFileRepository.count(specification);
    }

    /**
     * Function to convert TransactionAccountDataEntryFileCriteria to a {@link Specification}.
     */
    private Specification<TransactionAccountDataEntryFile> createSpecification(TransactionAccountDataEntryFileCriteria criteria) {
        Specification<TransactionAccountDataEntryFile> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TransactionAccountDataEntryFile_.id));
            }
            if (criteria.getPeriodFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPeriodFrom(), TransactionAccountDataEntryFile_.periodFrom));
            }
            if (criteria.getPeriodTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPeriodTo(), TransactionAccountDataEntryFile_.periodTo));
            }
            if (criteria.getUploadSuccessful() != null) {
                specification = specification.and(buildSpecification(criteria.getUploadSuccessful(), TransactionAccountDataEntryFile_.uploadSuccessful));
            }
            if (criteria.getUploadProcessed() != null) {
                specification = specification.and(buildSpecification(criteria.getUploadProcessed(), TransactionAccountDataEntryFile_.uploadProcessed));
            }
        }
        return specification;
    }
}
