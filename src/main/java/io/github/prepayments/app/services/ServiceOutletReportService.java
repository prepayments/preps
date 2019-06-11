package io.github.prepayments.app.services;

import io.github.prepayments.repository.ServiceOutletRepository;
import io.github.prepayments.service.dto.ServiceOutletDTO;
import io.github.prepayments.service.mapper.ServiceOutletMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class ServiceOutletReportService {

    private final ServiceOutletMapper serviceOutletMapper;
    private final ServiceOutletRepository serviceOutletRepository;

    public ServiceOutletReportService(final ServiceOutletMapper serviceOutletMapper, final ServiceOutletRepository serviceOutletRepository) {
        this.serviceOutletMapper = serviceOutletMapper;
        this.serviceOutletRepository = serviceOutletRepository;
    }

    @Transactional(readOnly = true)
    public List<ServiceOutletDTO> findAll() {
        log.debug("Request to get all service outlets for reporting");
        return serviceOutletMapper.toDto(serviceOutletRepository.findAll());
    }
}
