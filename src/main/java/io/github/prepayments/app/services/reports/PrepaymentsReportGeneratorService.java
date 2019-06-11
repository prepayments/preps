package io.github.prepayments.app.services.reports;

import io.github.prepayments.app.models.GeneratedReportDTO;
import io.github.prepayments.app.services.PrepaymentEntryReportService;
import io.github.prepayments.service.dto.PrepaymentEntryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static io.github.prepayments.app.services.FileInputUtil.createExcelFile;

@Slf4j
@Transactional
@Service("prepaymentReportGeneratorService")
public class PrepaymentsReportGeneratorService implements ReportGeneratorService {

    private final PrepaymentEntryReportService prepaymentEntryReportService;

    public PrepaymentsReportGeneratorService(final PrepaymentEntryReportService prepaymentEntryReportService) {
        this.prepaymentEntryReportService = prepaymentEntryReportService;
    }

    @Override
    public Optional<GeneratedReportDTO> generateReport(String reportTitle, String reportType) {

        List<PrepaymentEntryDTO> prepaymentEntries = prepaymentEntryReportService.findAll();

        log.debug("Fetched {} prepayment entry items from the prepaymentService", prepaymentEntries.size());

        byte[] dataFile = createExcelFile(prepaymentEntries, PrepaymentEntryDTO.class);

        GeneratedReportDTO dto = GeneratedReportDTO.builder()
                                                   .reportTitle(reportTitle)
                                                   .reportType(reportType)
                                                   .reportFile(dataFile)
                                                   .reportFileContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                                                   .build();

        return Optional.of(Objects.requireNonNull(dto));
    }
}
