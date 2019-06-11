package io.github.prepayments.app.services.reports;

import io.github.prepayments.app.models.GeneratedReportDTO;

import java.util.Optional;

public interface ReportGeneratorService {

    Optional<GeneratedReportDTO> generateReport(String reportTitle, String reportType);
}
