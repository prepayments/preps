package io.github.prepayments.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link io.github.prepayments.domain.ReportRequestEvent} entity.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ReportRequestEventDTO implements Serializable {

    private Long id;

    private LocalDate reportRequestDate;

    private String requestedBy;

    @Lob
    private byte[] reportFile;

    private String reportFileContentType;

    private Long reportTypeId;

    @Override
    public String toString() {
        return "ReportRequestEventDTO{" + "id=" + getId() + ", reportRequestDate='" + getReportRequestDate() + "'" + ", requestedBy='" + getRequestedBy() + "'" + ", reportType=" + getReportTypeId() +
            "}";
    }
}
