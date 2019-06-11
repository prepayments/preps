package io.github.prepayments.app.services;

import io.github.prepayments.repository.AmortizationEntryRepository;
import io.github.prepayments.service.dto.AmortizationEntryDTO;
import io.github.prepayments.service.mapper.AmortizationEntryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class AmortizationEntryReportService {

    private final AmortizationEntryMapper amortizationEntryMapper;
    private final AmortizationEntryRepository amortizationEntryRepository;

    public AmortizationEntryReportService(final AmortizationEntryMapper amortizationEntryMapper, final AmortizationEntryRepository amortizationEntryRepository) {
        this.amortizationEntryMapper = amortizationEntryMapper;
        this.amortizationEntryRepository = amortizationEntryRepository;
    }

    /**
     * Get all the amortizationEntries.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AmortizationEntryDTO> findAll() {
        log.debug("Request to get all AmortizationEntries for reporting");
        return amortizationEntryMapper.toDto(amortizationEntryRepository.findAll());
    }
}
