package io.github.prepayments.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link io.github.prepayments.domain.ReportRequestEvent} entity. This class is used
 * in {@link io.github.prepayments.web.rest.ReportRequestEventResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /report-request-events?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ReportRequestEventCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter reportRequestDate;

    private StringFilter requestedBy;

    private LongFilter reportTypeId;

    public ReportRequestEventCriteria(){
    }

    public ReportRequestEventCriteria(ReportRequestEventCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.reportRequestDate = other.reportRequestDate == null ? null : other.reportRequestDate.copy();
        this.requestedBy = other.requestedBy == null ? null : other.requestedBy.copy();
        this.reportTypeId = other.reportTypeId == null ? null : other.reportTypeId.copy();
    }

    @Override
    public ReportRequestEventCriteria copy() {
        return new ReportRequestEventCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getReportRequestDate() {
        return reportRequestDate;
    }

    public void setReportRequestDate(LocalDateFilter reportRequestDate) {
        this.reportRequestDate = reportRequestDate;
    }

    public StringFilter getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(StringFilter requestedBy) {
        this.requestedBy = requestedBy;
    }

    public LongFilter getReportTypeId() {
        return reportTypeId;
    }

    public void setReportTypeId(LongFilter reportTypeId) {
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
        final ReportRequestEventCriteria that = (ReportRequestEventCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(reportRequestDate, that.reportRequestDate) &&
            Objects.equals(requestedBy, that.requestedBy) &&
            Objects.equals(reportTypeId, that.reportTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        reportRequestDate,
        requestedBy,
        reportTypeId
        );
    }

    @Override
    public String toString() {
        return "ReportRequestEventCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (reportRequestDate != null ? "reportRequestDate=" + reportRequestDate + ", " : "") +
                (requestedBy != null ? "requestedBy=" + requestedBy + ", " : "") +
                (reportTypeId != null ? "reportTypeId=" + reportTypeId + ", " : "") +
            "}";
    }

}
