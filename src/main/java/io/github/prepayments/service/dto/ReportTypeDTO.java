package io.github.prepayments.service.dto;
import java.io.Serializable;
import java.util.Objects;
import io.github.prepayments.domain.enumeration.ReportMediumTypes;

/**
 * A DTO for the {@link io.github.prepayments.domain.ReportType} entity.
 */
public class ReportTypeDTO implements Serializable {

    private Long id;

    private String reportModelName;

    private ReportMediumTypes reportMediumType;

    private String reportPassword;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReportModelName() {
        return reportModelName;
    }

    public void setReportModelName(String reportModelName) {
        this.reportModelName = reportModelName;
    }

    public ReportMediumTypes getReportMediumType() {
        return reportMediumType;
    }

    public void setReportMediumType(ReportMediumTypes reportMediumType) {
        this.reportMediumType = reportMediumType;
    }

    public String getReportPassword() {
        return reportPassword;
    }

    public void setReportPassword(String reportPassword) {
        this.reportPassword = reportPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReportTypeDTO reportTypeDTO = (ReportTypeDTO) o;
        if (reportTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reportTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReportTypeDTO{" +
            "id=" + getId() +
            ", reportModelName='" + getReportModelName() + "'" +
            ", reportMediumType='" + getReportMediumType() + "'" +
            ", reportPassword='" + getReportPassword() + "'" +
            "}";
    }
}
