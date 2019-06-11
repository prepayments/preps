package io.github.prepayments.app.services.reports;

import io.github.prepayments.app.models.GeneratedReportDTO;
import io.github.prepayments.app.services.AmortizationEntryReportService;
import io.github.prepayments.service.dto.AmortizationEntryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static io.github.prepayments.app.services.FileInputUtil.createExcelFile;

@Slf4j
@Transactional
@Service("amortizationReportGeneratorService")
public class AmortizationReportGeneratorService implements ReportGeneratorService {


    private final AmortizationEntryReportService amortizationEntryReportService;

    public AmortizationReportGeneratorService(final AmortizationEntryReportService amortizationEntryReportService) {
        this.amortizationEntryReportService = amortizationEntryReportService;
    }

    @Override
    public Optional<GeneratedReportDTO> generateReport(String reportTitle, String reportType) {

        List<AmortizationEntryDTO> amortizationEntries = amortizationEntryReportService.findAll();

        log.debug("Fetched {} amortization entry items from the prepaymentService", amortizationEntries.size());

        byte[] dataFile = createExcelFile(amortizationEntries, AmortizationEntryDTO.class);

        GeneratedReportDTO dto = GeneratedReportDTO.builder()
                                                   .reportTitle(reportTitle)
                                                   .reportType(reportType)
                                                   .reportFile(dataFile)
                                                   .reportFileContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                                                   .build();

        return Optional.of(Objects.requireNonNull(dto));
    }
}
