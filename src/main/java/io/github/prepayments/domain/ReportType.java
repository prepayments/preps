package io.github.prepayments.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import io.github.prepayments.domain.enumeration.ReportMediumTypes;

/**
 * A ReportType.
 */
@Entity
@Table(name = "report_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ReportType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "report_model_name")
    private String reportModelName;

    @Enumerated(EnumType.STRING)
    @Column(name = "report_medium_type")
    private ReportMediumTypes reportMediumType;

    @Column(name = "report_password")
    private String reportPassword;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReportModelName() {
        return reportModelName;
    }

    public ReportType reportModelName(String reportModelName) {
        this.reportModelName = reportModelName;
        return this;
    }

    public void setReportModelName(String reportModelName) {
        this.reportModelName = reportModelName;
    }

    public ReportMediumTypes getReportMediumType() {
        return reportMediumType;
    }

    public ReportType reportMediumType(ReportMediumTypes reportMediumType) {
        this.reportMediumType = reportMediumType;
        return this;
    }

    public void setReportMediumType(ReportMediumTypes reportMediumType) {
        this.reportMediumType = reportMediumType;
    }

    public String getReportPassword() {
        return reportPassword;
    }

    public ReportType reportPassword(String reportPassword) {
        this.reportPassword = reportPassword;
        return this;
    }

    public void setReportPassword(String reportPassword) {
        this.reportPassword = reportPassword;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportType)) {
            return false;
        }
        return id != null && id.equals(((ReportType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ReportType{" +
            "id=" + getId() +
            ", reportModelName='" + getReportModelName() + "'" +
            ", reportMediumType='" + getReportMediumType() + "'" +
            ", reportPassword='" + getReportPassword() + "'" +
            "}";
    }
}
