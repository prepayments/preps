package io.github.prepayments.service;

import io.github.jhipster.service.QueryService;
import io.github.prepayments.domain.AmortizationUploadFile;
import io.github.prepayments.domain.AmortizationUploadFile_;
import io.github.prepayments.repository.AmortizationUploadFileRepository;
import io.github.prepayments.repository.search.AmortizationUploadFileSearchRepository;
import io.github.prepayments.service.dto.AmortizationUploadFileCriteria;
import io.github.prepayments.service.dto.AmortizationUploadFileDTO;
import io.github.prepayments.service.mapper.AmortizationUploadFileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for executing complex queries for {@link AmortizationUploadFile} entities in the database.
 * The main input is a {@link AmortizationUploadFileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AmortizationUploadFileDTO} or a {@link Page} of {@link AmortizationUploadFileDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AmortizationUploadFileQueryService extends QueryService<AmortizationUploadFile> {

    private final Logger log = LoggerFactory.getLogger(AmortizationUploadFileQueryService.class);

    private final AmortizationUploadFileRepository amortizationUploadFileRepository;

    private final AmortizationUploadFileMapper amortizationUploadFileMapper;

    private final AmortizationUploadFileSearchRepository amortizationUploadFileSearchRepository;

    public AmortizationUploadFileQueryService(AmortizationUploadFileRepository amortizationUploadFileRepository, AmortizationUploadFileMapper amortizationUploadFileMapper,
                                              AmortizationUploadFileSearchRepository amortizationUploadFileSearchRepository) {
        this.amortizationUploadFileRepository = amortizationUploadFileRepository;
        this.amortizationUploadFileMapper = amortizationUploadFileMapper;
        this.amortizationUploadFileSearchRepository = amortizationUploadFileSearchRepository;
    }

    /**
     * Return a {@link List} of {@link AmortizationUploadFileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AmortizationUploadFileDTO> findByCriteria(AmortizationUploadFileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AmortizationUploadFile> specification = createSpecification(criteria);
        return amortizationUploadFileMapper.toDto(amortizationUploadFileRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AmortizationUploadFileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AmortizationUploadFileDTO> findByCriteria(AmortizationUploadFileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AmortizationUploadFile> specification = createSpecification(criteria);
        return amortizationUploadFileRepository.findAll(specification, page).map(amortizationUploadFileMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AmortizationUploadFileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AmortizationUploadFile> specification = createSpecification(criteria);
        return amortizationUploadFileRepository.count(specification);
    }

    /**
     * Function to convert AmortizationUploadFileCriteria to a {@link Specification}.
     */
    private Specification<AmortizationUploadFile> createSpecification(AmortizationUploadFileCriteria criteria) {
        Specification<AmortizationUploadFile> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), AmortizationUploadFile_.id));
            }
            if (criteria.getPeriodFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPeriodFrom(), AmortizationUploadFile_.periodFrom));
            }
            if (criteria.getPeriodTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPeriodTo(), AmortizationUploadFile_.periodTo));
            }
            if (criteria.getUploadSuccessful() != null) {
                specification = specification.and(buildSpecification(criteria.getUploadSuccessful(), AmortizationUploadFile_.uploadSuccessful));
            }
            if (criteria.getUploadProcessed() != null) {
                specification = specification.and(buildSpecification(criteria.getUploadProcessed(), AmortizationUploadFile_.uploadProcessed));
            }
            if (criteria.getEntriesCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEntriesCount(), AmortizationUploadFile_.entriesCount));
            }
            if (criteria.getFileToken() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileToken(), AmortizationUploadFile_.fileToken));
            }
        }
        return specification;
    }
}
