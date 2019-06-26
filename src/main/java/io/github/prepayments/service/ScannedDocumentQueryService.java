package io.github.prepayments.service;

import io.github.jhipster.service.QueryService;
import io.github.prepayments.domain.ScannedDocument;
import io.github.prepayments.domain.ScannedDocument_;
import io.github.prepayments.repository.ScannedDocumentRepository;
import io.github.prepayments.repository.search.ScannedDocumentSearchRepository;
import io.github.prepayments.service.dto.ScannedDocumentCriteria;
import io.github.prepayments.service.dto.ScannedDocumentDTO;
import io.github.prepayments.service.mapper.ScannedDocumentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for executing complex queries for {@link ScannedDocument} entities in the database. The main input is a {@link ScannedDocumentCriteria} which gets converted to {@link Specification}, in a
 * way that all the filters must apply. It returns a {@link List} of {@link ScannedDocumentDTO} or a {@link Page} of {@link ScannedDocumentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ScannedDocumentQueryService extends QueryService<ScannedDocument> {

    private final Logger log = LoggerFactory.getLogger(ScannedDocumentQueryService.class);

    private final ScannedDocumentRepository scannedDocumentRepository;

    private final ScannedDocumentMapper scannedDocumentMapper;

    private final ScannedDocumentSearchRepository scannedDocumentSearchRepository;

    public ScannedDocumentQueryService(ScannedDocumentRepository scannedDocumentRepository, ScannedDocumentMapper scannedDocumentMapper,
                                       ScannedDocumentSearchRepository scannedDocumentSearchRepository) {
        this.scannedDocumentRepository = scannedDocumentRepository;
        this.scannedDocumentMapper = scannedDocumentMapper;
        this.scannedDocumentSearchRepository = scannedDocumentSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ScannedDocumentDTO} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ScannedDocumentDTO> findByCriteria(ScannedDocumentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ScannedDocument> specification = createSpecification(criteria);
        return scannedDocumentMapper.toDto(scannedDocumentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ScannedDocumentDTO} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ScannedDocumentDTO> findByCriteria(ScannedDocumentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ScannedDocument> specification = createSpecification(criteria);
        return scannedDocumentRepository.findAll(specification, page).map(scannedDocumentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ScannedDocumentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ScannedDocument> specification = createSpecification(criteria);
        return scannedDocumentRepository.count(specification);
    }

    /**
     * Function to convert ScannedDocumentCriteria to a {@link Specification}.
     */
    private Specification<ScannedDocument> createSpecification(ScannedDocumentCriteria criteria) {
        Specification<ScannedDocument> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ScannedDocument_.id));
            }
            if (criteria.getDocumentName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentName(), ScannedDocument_.documentName));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ScannedDocument_.description));
            }
            if (criteria.getInvoiceNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInvoiceNumber(), ScannedDocument_.invoiceNumber));
            }
            if (criteria.getTransactionId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTransactionId(), ScannedDocument_.transactionId));
            }
            if (criteria.getTransactionDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTransactionDate(), ScannedDocument_.transactionDate));
            }
        }
        return specification;
    }
}
