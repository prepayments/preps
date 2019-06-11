package io.github.prepayments.service.impl;

import io.github.prepayments.service.ReportTypeService;
import io.github.prepayments.domain.ReportType;
import io.github.prepayments.repository.ReportTypeRepository;
import io.github.prepayments.service.dto.ReportTypeDTO;
import io.github.prepayments.service.mapper.ReportTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ReportType}.
 */
@Service
@Transactional
public class ReportTypeServiceImpl implements ReportTypeService {

    private final Logger log = LoggerFactory.getLogger(ReportTypeServiceImpl.class);

    private final ReportTypeRepository reportTypeRepository;

    private final ReportTypeMapper reportTypeMapper;

    public ReportTypeServiceImpl(ReportTypeRepository reportTypeRepository, ReportTypeMapper reportTypeMapper) {
        this.reportTypeRepository = reportTypeRepository;
        this.reportTypeMapper = reportTypeMapper;
    }

    /**
     * Save a reportType.
     *
     * @param reportTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ReportTypeDTO save(ReportTypeDTO reportTypeDTO) {
        log.debug("Request to save ReportType : {}", reportTypeDTO);
        ReportType reportType = reportTypeMapper.toEntity(reportTypeDTO);
        reportType = reportTypeRepository.save(reportType);
        return reportTypeMapper.toDto(reportType);
    }

    /**
     * Get all the reportTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ReportTypeDTO> findAll() {
        log.debug("Request to get all ReportTypes");
        return reportTypeRepository.findAll().stream()
            .map(reportTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one reportType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ReportTypeDTO> findOne(Long id) {
        log.debug("Request to get ReportType : {}", id);
        return reportTypeRepository.findById(id)
            .map(reportTypeMapper::toDto);
    }

    /**
     * Delete the reportType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReportType : {}", id);
        reportTypeRepository.deleteById(id);
    }
}
