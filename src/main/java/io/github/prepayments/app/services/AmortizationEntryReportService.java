package io.github.prepayments.app.services;

import io.github.prepayments.repository.AmortizationEntryRepository;
import io.github.prepayments.service.dto.AmortizationEntryDTO;
import io.github.prepayments.service.dto.PrepaymentEntryDTO;
import io.github.prepayments.service.mapper.AmortizationEntryMapper;
import io.github.prepayments.service.mapper.PrepaymentEntryMapper;
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
    private final PrepaymentEntryMapper prepaymentEntryMapper;

    public AmortizationEntryReportService(final AmortizationEntryMapper amortizationEntryMapper, final AmortizationEntryRepository amortizationEntryRepository,
                                          final PrepaymentEntryMapper prepaymentEntryMapper) {
        this.amortizationEntryMapper = amortizationEntryMapper;
        this.amortizationEntryRepository = amortizationEntryRepository;
        this.prepaymentEntryMapper = prepaymentEntryMapper;
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

    /**
     * Get all the amortizationEntries.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AmortizationEntryDTO> findAllByPrepaymentId(final PrepaymentEntryDTO prepaymentEntryDTO) {

        log.debug("Request to get all AmortizationEntries who prepayment entry id is : {}", prepaymentEntryDTO.getId());

        return amortizationEntryMapper.toDto(amortizationEntryRepository.findAllByPrepaymentEntryIs(prepaymentEntryMapper.toEntity(prepaymentEntryDTO)));
    }
}
