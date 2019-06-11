package io.github.prepayments.app.services;

import io.github.prepayments.repository.RegisteredSupplierRepository;
import io.github.prepayments.service.dto.RegisteredSupplierDTO;
import io.github.prepayments.service.mapper.RegisteredSupplierMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class RegisteredSupplierReportService {

    private final RegisteredSupplierMapper registeredSupplierMapper;
    private final RegisteredSupplierRepository registeredSupplierRepository;

    public RegisteredSupplierReportService(final RegisteredSupplierMapper registeredSupplierMapper, final RegisteredSupplierRepository registeredSupplierRepository) {
        this.registeredSupplierMapper = registeredSupplierMapper;
        this.registeredSupplierRepository = registeredSupplierRepository;
    }

    @Transactional(readOnly = true)
    public List<RegisteredSupplierDTO> findAll() {
        log.debug("Request to get all suppliers for reporting");
        return registeredSupplierMapper.toDto(registeredSupplierRepository.findAll());
    }
}
