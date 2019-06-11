package io.github.prepayments.service.impl;

import io.github.prepayments.service.ServiceOutletService;
import io.github.prepayments.domain.ServiceOutlet;
import io.github.prepayments.repository.ServiceOutletRepository;
import io.github.prepayments.service.dto.ServiceOutletDTO;
import io.github.prepayments.service.mapper.ServiceOutletMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceOutlet}.
 */
@Service
@Transactional
public class ServiceOutletServiceImpl implements ServiceOutletService {

    private final Logger log = LoggerFactory.getLogger(ServiceOutletServiceImpl.class);

    private final ServiceOutletRepository serviceOutletRepository;

    private final ServiceOutletMapper serviceOutletMapper;

    public ServiceOutletServiceImpl(ServiceOutletRepository serviceOutletRepository, ServiceOutletMapper serviceOutletMapper) {
        this.serviceOutletRepository = serviceOutletRepository;
        this.serviceOutletMapper = serviceOutletMapper;
    }

    /**
     * Save a serviceOutlet.
     *
     * @param serviceOutletDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ServiceOutletDTO save(ServiceOutletDTO serviceOutletDTO) {
        log.debug("Request to save ServiceOutlet : {}", serviceOutletDTO);
        ServiceOutlet serviceOutlet = serviceOutletMapper.toEntity(serviceOutletDTO);
        serviceOutlet = serviceOutletRepository.save(serviceOutlet);
        return serviceOutletMapper.toDto(serviceOutlet);
    }

    /**
     * Get all the serviceOutlets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServiceOutletDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceOutlets");
        return serviceOutletRepository.findAll(pageable)
            .map(serviceOutletMapper::toDto);
    }


    /**
     * Get one serviceOutlet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceOutletDTO> findOne(Long id) {
        log.debug("Request to get ServiceOutlet : {}", id);
        return serviceOutletRepository.findById(id)
            .map(serviceOutletMapper::toDto);
    }

    /**
     * Delete the serviceOutlet by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceOutlet : {}", id);
        serviceOutletRepository.deleteById(id);
    }
}
