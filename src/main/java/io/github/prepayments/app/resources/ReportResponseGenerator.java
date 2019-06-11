package io.github.prepayments.app.resources;

import io.github.prepayments.app.models.GeneratedReportDTO;
import org.springframework.http.ResponseEntity;

/**
 * This object creates a ResponseEntity with a generated report
 */
public interface ReportResponseGenerator {

    ResponseEntity<GeneratedReportDTO> generatedReportResponseEntity();
}
