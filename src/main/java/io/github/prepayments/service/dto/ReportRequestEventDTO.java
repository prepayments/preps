package io.github.prepayments.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link io.github.prepayments.domain.ReportRequestEvent} entity.
 */
public class ReportRequestEventDTO implements Serializable {

    private Long id;

    private LocalDate reportRequestDate;

    private String requestedBy;

    @Lob
    private byte[] reportFile;

    private String reportFileContentType;

    private Long reportTypeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getReportRequestDate() {
        return reportRequestDate;
    }

    public void setReportRequestDate(LocalDate reportRequestDate) {
        this.reportRequestDate = reportRequestDate;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public byte[] getReportFile() {
        return reportFile;
    }

    public void setReportFile(byte[] reportFile) {
        this.reportFile = reportFile;
    }

    public String getReportFileContentType() {
        return reportFileContentType;
    }

    public void setReportFileContentType(String reportFileContentType) {
        this.reportFileContentType = reportFileContentType;
    }

    public Long getReportTypeId() {
        return reportTypeId;
    }

    public void setReportTypeId(Long reportTypeId) {
        this.reportTypeId = reportTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReportRequestEventDTO reportRequestEventDTO = (ReportRequestEventDTO) o;
        if (reportRequestEventDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reportRequestEventDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReportRequestEventDTO{" +
            "id=" + getId() +
            ", reportRequestDate='" + getReportRequestDate() + "'" +
            ", requestedBy='" + getRequestedBy() + "'" +
            ", reportFile='" + getReportFile() + "'" +
            ", reportType=" + getReportTypeId() +
            "}";
    }
}
