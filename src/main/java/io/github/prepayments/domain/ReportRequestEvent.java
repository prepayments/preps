package io.github.prepayments.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A ReportRequestEvent.
 */
@Entity
@Table(name = "report_request_event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ReportRequestEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "report_request_date")
    private LocalDate reportRequestDate;

    @Column(name = "requested_by")
    private String requestedBy;

    @Lob
    @Column(name = "report_file")
    private byte[] reportFile;

    @Column(name = "report_file_content_type")
    private String reportFileContentType;

    @ManyToOne
    @JsonIgnoreProperties("reportRequestEvents")
    private ReportType reportType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getReportRequestDate() {
        return reportRequestDate;
    }

    public ReportRequestEvent reportRequestDate(LocalDate reportRequestDate) {
        this.reportRequestDate = reportRequestDate;
        return this;
    }

    public void setReportRequestDate(LocalDate reportRequestDate) {
        this.reportRequestDate = reportRequestDate;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public ReportRequestEvent requestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
        return this;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public byte[] getReportFile() {
        return reportFile;
    }

    public ReportRequestEvent reportFile(byte[] reportFile) {
        this.reportFile = reportFile;
        return this;
    }

    public void setReportFile(byte[] reportFile) {
        this.reportFile = reportFile;
    }

    public String getReportFileContentType() {
        return reportFileContentType;
    }

    public ReportRequestEvent reportFileContentType(String reportFileContentType) {
        this.reportFileContentType = reportFileContentType;
        return this;
    }

    public void setReportFileContentType(String reportFileContentType) {
        this.reportFileContentType = reportFileContentType;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public ReportRequestEvent reportType(ReportType reportType) {
        this.reportType = reportType;
        return this;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportRequestEvent)) {
            return false;
        }
        return id != null && id.equals(((ReportRequestEvent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ReportRequestEvent{" +
            "id=" + getId() +
            ", reportRequestDate='" + getReportRequestDate() + "'" +
            ", requestedBy='" + getRequestedBy() + "'" +
            ", reportFile='" + getReportFile() + "'" +
            ", reportFileContentType='" + getReportFileContentType() + "'" +
            "}";
    }
}
