package io.github.prepayments.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.prepayments.domain.enumeration.ReportMediumTypes;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link io.github.prepayments.domain.ReportType} entity. This class is used in {@link io.github.prepayments.web.rest.ReportTypeResource} to receive all the possible filtering
 * options from the Http GET request parameters. For example the following could be a valid request: {@code /report-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false} As Spring is
 * unable to properly convert the types, unless specific {@link Filter} class are used, we need to use fix type specific filters.
 */
public class ReportTypeCriteria implements Serializable, Criteria {
    private static final long serialVersionUID = 1L;
    private LongFilter id;
    private StringFilter reportModelName;
    private ReportMediumTypesFilter reportMediumType;
    private StringFilter reportPassword;

    public ReportTypeCriteria() {
    }

    public ReportTypeCriteria(ReportTypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.reportModelName = other.reportModelName == null ? null : other.reportModelName.copy();
        this.reportMediumType = other.reportMediumType == null ? null : other.reportMediumType.copy();
        this.reportPassword = other.reportPassword == null ? null : other.reportPassword.copy();
    }

    @Override
    public ReportTypeCriteria copy() {
        return new ReportTypeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getReportModelName() {
        return reportModelName;
    }

    public void setReportModelName(StringFilter reportModelName) {
        this.reportModelName = reportModelName;
    }

    public ReportMediumTypesFilter getReportMediumType() {
        return reportMediumType;
    }

    public void setReportMediumType(ReportMediumTypesFilter reportMediumType) {
        this.reportMediumType = reportMediumType;
    }

    public StringFilter getReportPassword() {
        return reportPassword;
    }

    public void setReportPassword(StringFilter reportPassword) {
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
        final ReportTypeCriteria that = (ReportTypeCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(reportModelName, that.reportModelName) && Objects.equals(reportMediumType, that.reportMediumType) &&
            Objects.equals(reportPassword, that.reportPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reportModelName, reportMediumType, reportPassword);
    }

    @Override
    public String toString() {
        return "ReportTypeCriteria{" + (id != null ? "id=" + id + ", " : "") + (reportModelName != null ? "reportModelName=" + reportModelName + ", " : "") +
            (reportMediumType != null ? "reportMediumType=" + reportMediumType + ", " : "") + (reportPassword != null ? "reportPassword=" + reportPassword + ", " : "") + "}";
    }

    /**
     * Class for filtering ReportMediumTypes
     */
    public static class ReportMediumTypesFilter extends Filter<ReportMediumTypes> {

        public ReportMediumTypesFilter() {
        }

        public ReportMediumTypesFilter(ReportMediumTypesFilter filter) {
            super(filter);
        }

        @Override
        public ReportMediumTypesFilter copy() {
            return new ReportMediumTypesFilter(this);
        }

    }

}
