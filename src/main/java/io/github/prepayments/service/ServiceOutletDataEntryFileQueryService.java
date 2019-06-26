package io.github.prepayments.service;

import io.github.jhipster.service.QueryService;
import io.github.prepayments.domain.ServiceOutletDataEntryFile;
import io.github.prepayments.domain.ServiceOutletDataEntryFile_;
import io.github.prepayments.repository.ServiceOutletDataEntryFileRepository;
import io.github.prepayments.service.dto.ServiceOutletDataEntryFileCriteria;
import io.github.prepayments.service.dto.ServiceOutletDataEntryFileDTO;
import io.github.prepayments.service.mapper.ServiceOutletDataEntryFileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for executing complex queries for {@link ServiceOutletDataEntryFile} entities in the database. The main input is a {@link ServiceOutletDataEntryFileCriteria} which gets converted to {@link
 * Specification}, in a way that all the filters must apply. It returns a {@link List} of {@link ServiceOutletDataEntryFileDTO} or a {@link Page} of {@link ServiceOutletDataEntryFileDTO} which
 * fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServiceOutletDataEntryFileQueryService extends QueryService<ServiceOutletDataEntryFile> {

    private final Logger log = LoggerFactory.getLogger(ServiceOutletDataEntryFileQueryService.class);

    private final ServiceOutletDataEntryFileRepository serviceOutletDataEntryFileRepository;

    private final ServiceOutletDataEntryFileMapper serviceOutletDataEntryFileMapper;

    public ServiceOutletDataEntryFileQueryService(ServiceOutletDataEntryFileRepository serviceOutletDataEntryFileRepository, ServiceOutletDataEntryFileMapper serviceOutletDataEntryFileMapper) {
        this.serviceOutletDataEntryFileRepository = serviceOutletDataEntryFileRepository;
        this.serviceOutletDataEntryFileMapper = serviceOutletDataEntryFileMapper;
    }

    /**
     * Return a {@link List} of {@link ServiceOutletDataEntryFileDTO} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceOutletDataEntryFileDTO> findByCriteria(ServiceOutletDataEntryFileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ServiceOutletDataEntryFile> specification = createSpecification(criteria);
        return serviceOutletDataEntryFileMapper.toDto(serviceOutletDataEntryFileRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ServiceOutletDataEntryFileDTO} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceOutletDataEntryFileDTO> findByCriteria(ServiceOutletDataEntryFileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ServiceOutletDataEntryFile> specification = createSpecification(criteria);
        return serviceOutletDataEntryFileRepository.findAll(specification, page).map(serviceOutletDataEntryFileMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServiceOutletDataEntryFileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ServiceOutletDataEntryFile> specification = createSpecification(criteria);
        return serviceOutletDataEntryFileRepository.count(specification);
    }

    /**
     * Function to convert ServiceOutletDataEntryFileCriteria to a {@link Specification}.
     */
    private Specification<ServiceOutletDataEntryFile> createSpecification(ServiceOutletDataEntryFileCriteria criteria) {
        Specification<ServiceOutletDataEntryFile> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ServiceOutletDataEntryFile_.id));
            }
            if (criteria.getPeriodFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPeriodFrom(), ServiceOutletDataEntryFile_.periodFrom));
            }
            if (criteria.getPeriodTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPeriodTo(), ServiceOutletDataEntryFile_.periodTo));
            }
            if (criteria.getUploadSuccessful() != null) {
                specification = specification.and(buildSpecification(criteria.getUploadSuccessful(), ServiceOutletDataEntryFile_.uploadSuccessful));
            }
            if (criteria.getUploadProcessed() != null) {
                specification = specification.and(buildSpecification(criteria.getUploadProcessed(), ServiceOutletDataEntryFile_.uploadProcessed));
            }
        }
        return specification;
    }
}
