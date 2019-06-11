package io.github.prepayments.app.services.reports;

import io.github.prepayments.app.models.GeneratedReportDTO;
import io.github.prepayments.app.services.ServiceOutletReportService;
import io.github.prepayments.service.dto.ServiceOutletDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static io.github.prepayments.app.services.FileInputUtil.createExcelFile;

@Slf4j
@Transactional
@Service("serviceOutletReportGeneratorService")
public class ServiceOutletReportGeneratorService implements ReportGeneratorService {

    private final ServiceOutletReportService serviceOutletReportService;

    public ServiceOutletReportGeneratorService(final ServiceOutletReportService serviceOutletReportService) {
        this.serviceOutletReportService = serviceOutletReportService;
    }

    @Override
    public Optional<GeneratedReportDTO> generateReport(String reportTitle, String reportType) {

        List<ServiceOutletDTO> serviceOutlets = serviceOutletReportService.findAll();

        log.debug("Fetched {} service outlet entry items from the serviceOutletCatalogue", serviceOutlets.size());

        byte[] dataFile = createExcelFile(serviceOutlets, ServiceOutletDTO.class);

        GeneratedReportDTO dto = GeneratedReportDTO.builder()
                                                   .reportTitle(reportTitle)
                                                   .reportType(reportType)
                                                   .reportFile(dataFile)
                                                   .reportFileContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                                                   .build();

        return Optional.of(Objects.requireNonNull(dto));
    }
}
