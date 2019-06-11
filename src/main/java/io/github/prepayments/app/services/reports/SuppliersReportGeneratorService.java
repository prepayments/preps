package io.github.prepayments.app.services.reports;

import io.github.prepayments.app.models.GeneratedReportDTO;
import io.github.prepayments.app.services.RegisteredSupplierReportService;
import io.github.prepayments.service.dto.RegisteredSupplierDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static io.github.prepayments.app.services.FileInputUtil.createExcelFile;

@Slf4j
@Transactional
@Service("suppliersReportGeneratorService")
public class SuppliersReportGeneratorService implements ReportGeneratorService {

    private final RegisteredSupplierReportService registeredSupplierReportService;

    public SuppliersReportGeneratorService(final RegisteredSupplierReportService registeredSupplierReportService) {
        this.registeredSupplierReportService = registeredSupplierReportService;
    }

    @Override
    public Optional<GeneratedReportDTO> generateReport(String reportTitle, String reportType) {

        List<RegisteredSupplierDTO> suppliers = registeredSupplierReportService.findAll();

        log.debug("Fetched {} registered supplier entry items from the prepaymentsSupplierCatalogue", suppliers.size());

        byte[] dataFile = createExcelFile(suppliers, RegisteredSupplierDTO.class);

        GeneratedReportDTO dto = GeneratedReportDTO.builder()
                                                   .reportTitle(reportTitle)
                                                   .reportType(reportType)
                                                   .reportFile(dataFile)
                                                   .reportFileContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                                                   .build();

        return Optional.of(Objects.requireNonNull(dto));
    }
}
