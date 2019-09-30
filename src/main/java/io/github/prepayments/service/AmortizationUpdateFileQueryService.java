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

import io.github.prepayments.domain.AmortizationUpdateFile;
import io.github.prepayments.domain.*; // for static metamodels
import io.github.prepayments.repository.AmortizationUpdateFileRepository;
import io.github.prepayments.repository.search.AmortizationUpdateFileSearchRepository;
import io.github.prepayments.service.dto.AmortizationUpdateFileCriteria;
import io.github.prepayments.service.dto.AmortizationUpdateFileDTO;
import io.github.prepayments.service.mapper.AmortizationUpdateFileMapper;

/**
 * Service for executing complex queries for {@link AmortizationUpdateFile} entities in the database.
 * The main input is a {@link AmortizationUpdateFileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AmortizationUpdateFileDTO} or a {@link Page} of {@link AmortizationUpdateFileDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AmortizationUpdateFileQueryService extends QueryService<AmortizationUpdateFile> {

    private final Logger log = LoggerFactory.getLogger(AmortizationUpdateFileQueryService.class);

    private final AmortizationUpdateFileRepository amortizationUpdateFileRepository;

    private final AmortizationUpdateFileMapper amortizationUpdateFileMapper;

    private final AmortizationUpdateFileSearchRepository amortizationUpdateFileSearchRepository;

    public AmortizationUpdateFileQueryService(AmortizationUpdateFileRepository amortizationUpdateFileRepository, AmortizationUpdateFileMapper amortizationUpdateFileMapper, AmortizationUpdateFileSearchRepository amortizationUpdateFileSearchRepository) {
        this.amortizationUpdateFileRepository = amortizationUpdateFileRepository;
        this.amortizationUpdateFileMapper = amortizationUpdateFileMapper;
        this.amortizationUpdateFileSearchRepository = amortizationUpdateFileSearchRepository;
    }

    /**
     * Return a {@link List} of {@link AmortizationUpdateFileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AmortizationUpdateFileDTO> findByCriteria(AmortizationUpdateFileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AmortizationUpdateFile> specification = createSpecification(criteria);
        return amortizationUpdateFileMapper.toDto(amortizationUpdateFileRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AmortizationUpdateFileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AmortizationUpdateFileDTO> findByCriteria(AmortizationUpdateFileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AmortizationUpdateFile> specification = createSpecification(criteria);
        return amortizationUpdateFileRepository.findAll(specification, page)
            .map(amortizationUpdateFileMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AmortizationUpdateFileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AmortizationUpdateFile> specification = createSpecification(criteria);
        return amortizationUpdateFileRepository.count(specification);
    }

    /**
     * Function to convert AmortizationUpdateFileCriteria to a {@link Specification}.
     */
    private Specification<AmortizationUpdateFile> createSpecification(AmortizationUpdateFileCriteria criteria) {
        Specification<AmortizationUpdateFile> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), AmortizationUpdateFile_.id));
            }
            if (criteria.getNarration() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNarration(), AmortizationUpdateFile_.narration));
            }
            if (criteria.getUploadSuccessful() != null) {
                specification = specification.and(buildSpecification(criteria.getUploadSuccessful(), AmortizationUpdateFile_.uploadSuccessful));
            }
            if (criteria.getUploadProcessed() != null) {
                specification = specification.and(buildSpecification(criteria.getUploadProcessed(), AmortizationUpdateFile_.uploadProcessed));
            }
            if (criteria.getEntriesCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEntriesCount(), AmortizationUpdateFile_.entriesCount));
            }
            if (criteria.getFileToken() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileToken(), AmortizationUpdateFile_.fileToken));
            }
            if (criteria.getReasonForUpdate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReasonForUpdate(), AmortizationUpdateFile_.reasonForUpdate));
            }
        }
        return specification;
    }
}
