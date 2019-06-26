package io.github.prepayments.service.impl;

import io.github.prepayments.domain.ServiceOutletDataEntryFile;
import io.github.prepayments.repository.ServiceOutletDataEntryFileRepository;
import io.github.prepayments.service.ServiceOutletDataEntryFileService;
import io.github.prepayments.service.dto.ServiceOutletDataEntryFileDTO;
import io.github.prepayments.service.mapper.ServiceOutletDataEntryFileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceOutletDataEntryFile}.
 */
@Service
@Transactional
public class ServiceOutletDataEntryFileServiceImpl implements ServiceOutletDataEntryFileService {

    private final Logger log = LoggerFactory.getLogger(ServiceOutletDataEntryFileServiceImpl.class);

    private final ServiceOutletDataEntryFileRepository serviceOutletDataEntryFileRepository;

    private final ServiceOutletDataEntryFileMapper serviceOutletDataEntryFileMapper;

    public ServiceOutletDataEntryFileServiceImpl(ServiceOutletDataEntryFileRepository serviceOutletDataEntryFileRepository, ServiceOutletDataEntryFileMapper serviceOutletDataEntryFileMapper) {
        this.serviceOutletDataEntryFileRepository = serviceOutletDataEntryFileRepository;
        this.serviceOutletDataEntryFileMapper = serviceOutletDataEntryFileMapper;
    }

    /**
     * Save a serviceOutletDataEntryFile.
     *
     * @param serviceOutletDataEntryFileDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ServiceOutletDataEntryFileDTO save(ServiceOutletDataEntryFileDTO serviceOutletDataEntryFileDTO) {
        log.debug("Request to save ServiceOutletDataEntryFile : {}", serviceOutletDataEntryFileDTO);
        ServiceOutletDataEntryFile serviceOutletDataEntryFile = serviceOutletDataEntryFileMapper.toEntity(serviceOutletDataEntryFileDTO);
        serviceOutletDataEntryFile = serviceOutletDataEntryFileRepository.save(serviceOutletDataEntryFile);
        return serviceOutletDataEntryFileMapper.toDto(serviceOutletDataEntryFile);
    }

    /**
     * Get all the serviceOutletDataEntryFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServiceOutletDataEntryFileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceOutletDataEntryFiles");
        return serviceOutletDataEntryFileRepository.findAll(pageable).map(serviceOutletDataEntryFileMapper::toDto);
    }


    /**
     * Get one serviceOutletDataEntryFile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceOutletDataEntryFileDTO> findOne(Long id) {
        log.debug("Request to get ServiceOutletDataEntryFile : {}", id);
        return serviceOutletDataEntryFileRepository.findById(id).map(serviceOutletDataEntryFileMapper::toDto);
    }

    /**
     * Delete the serviceOutletDataEntryFile by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceOutletDataEntryFile : {}", id);
        serviceOutletDataEntryFileRepository.deleteById(id);
    }
}
