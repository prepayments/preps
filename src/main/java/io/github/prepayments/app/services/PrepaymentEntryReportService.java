package io.github.prepayments.app.services;

import io.github.prepayments.repository.PrepaymentEntryRepository;
import io.github.prepayments.service.dto.PrepaymentEntryDTO;
import io.github.prepayments.service.mapper.PrepaymentEntryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class PrepaymentEntryReportService {

    private final PrepaymentEntryMapper prepaymentEntryMapper;
    private final PrepaymentEntryRepository prepaymentEntryRepository;

    public PrepaymentEntryReportService(final PrepaymentEntryMapper prepaymentEntryMapper, final PrepaymentEntryRepository prepaymentEntryRepository) {
        this.prepaymentEntryMapper = prepaymentEntryMapper;
        this.prepaymentEntryRepository = prepaymentEntryRepository;
    }

    /**
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PrepaymentEntryDTO> findAll() {
        log.debug("Request to get all Prepayment entries for reporting");
        return prepaymentEntryMapper.toDto(prepaymentEntryRepository.findAll());
    }
}
