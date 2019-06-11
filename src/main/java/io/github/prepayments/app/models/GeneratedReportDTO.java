package io.github.prepayments.app.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is a testing type report DTO for transferring excel files over API
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GeneratedReportDTO {

    private String reportTitle;

    private String reportType;

    private byte[] reportFile;

    private String reportFileContentType;

}
