package io.github.prepayments.service;

import io.github.jhipster.service.QueryService;
import io.github.prepayments.domain.PrepaymentDataEntryFile;
import io.github.prepayments.domain.PrepaymentDataEntryFile_;
import io.github.prepayments.repository.PrepaymentDataEntryFileRepository;
import io.github.prepayments.service.dto.PrepaymentDataEntryFileCriteria;
import io.github.prepayments.service.dto.PrepaymentDataEntryFileDTO;
import io.github.prepayments.service.mapper.PrepaymentDataEntryFileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for executing complex queries for {@link PrepaymentDataEntryFile} entities in the database.
 * The main input is a {@link PrepaymentDataEntryFileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PrepaymentDataEntryFileDTO} or a {@link Page} of {@link PrepaymentDataEntryFileDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PrepaymentDataEntryFileQueryService extends QueryService<PrepaymentDataEntryFile> {

    private final Logger log = LoggerFactory.getLogger(PrepaymentDataEntryFileQueryService.class);

    private final PrepaymentDataEntryFileRepository prepaymentDataEntryFileRepository;

    private final PrepaymentDataEntryFileMapper prepaymentDataEntryFileMapper;

    public PrepaymentDataEntryFileQueryService(PrepaymentDataEntryFileRepository prepaymentDataEntryFileRepository, PrepaymentDataEntryFileMapper prepaymentDataEntryFileMapper) {
        this.prepaymentDataEntryFileRepository = prepaymentDataEntryFileRepository;
        this.prepaymentDataEntryFileMapper = prepaymentDataEntryFileMapper;
    }

    /**
     * Return a {@link List} of {@link PrepaymentDataEntryFileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PrepaymentDataEntryFileDTO> findByCriteria(PrepaymentDataEntryFileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PrepaymentDataEntryFile> specification = createSpecification(criteria);
        return prepaymentDataEntryFileMapper.toDto(prepaymentDataEntryFileRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PrepaymentDataEntryFileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PrepaymentDataEntryFileDTO> findByCriteria(PrepaymentDataEntryFileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PrepaymentDataEntryFile> specification = createSpecification(criteria);
        return prepaymentDataEntryFileRepository.findAll(specification, page).map(prepaymentDataEntryFileMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PrepaymentDataEntryFileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PrepaymentDataEntryFile> specification = createSpecification(criteria);
        return prepaymentDataEntryFileRepository.count(specification);
    }

    /**
     * Function to convert PrepaymentDataEntryFileCriteria to a {@link Specification}.
     */
    private Specification<PrepaymentDataEntryFile> createSpecification(PrepaymentDataEntryFileCriteria criteria) {
        Specification<PrepaymentDataEntryFile> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PrepaymentDataEntryFile_.id));
            }
            if (criteria.getPeriodFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPeriodFrom(), PrepaymentDataEntryFile_.periodFrom));
            }
            if (criteria.getPeriodTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPeriodTo(), PrepaymentDataEntryFile_.periodTo));
            }
            if (criteria.getUploadProcessed() != null) {
                specification = specification.and(buildSpecification(criteria.getUploadProcessed(), PrepaymentDataEntryFile_.uploadProcessed));
            }
            if (criteria.getUploadSuccessful() != null) {
                specification = specification.and(buildSpecification(criteria.getUploadSuccessful(), PrepaymentDataEntryFile_.uploadSuccessful));
            }
            if (criteria.getEntriesCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEntriesCount(), PrepaymentDataEntryFile_.entriesCount));
            }
            if (criteria.getFileToken() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileToken(), PrepaymentDataEntryFile_.fileToken));
            }
        }
        return specification;
    }
}
