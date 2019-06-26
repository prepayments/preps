package io.github.prepayments.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.LongFilter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link io.github.prepayments.domain.PrepaymentDataEntryFile} entity. This class is used in {@link io.github.prepayments.web.rest.PrepaymentDataEntryFileResource} to receive
 * all the possible filtering options from the Http GET request parameters. For example the following could be a valid request: {@code /prepayment-data-entry-files?id
 * .greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use fix type specific filters.
 */
public class PrepaymentDataEntryFileCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter periodFrom;

    private LocalDateFilter periodTo;

    private BooleanFilter uploadProcessed;

    private BooleanFilter uploadSuccessful;

    public PrepaymentDataEntryFileCriteria() {
    }

    public PrepaymentDataEntryFileCriteria(PrepaymentDataEntryFileCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.periodFrom = other.periodFrom == null ? null : other.periodFrom.copy();
        this.periodTo = other.periodTo == null ? null : other.periodTo.copy();
        this.uploadProcessed = other.uploadProcessed == null ? null : other.uploadProcessed.copy();
        this.uploadSuccessful = other.uploadSuccessful == null ? null : other.uploadSuccessful.copy();
    }

    @Override
    public PrepaymentDataEntryFileCriteria copy() {
        return new PrepaymentDataEntryFileCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getPeriodFrom() {
        return periodFrom;
    }

    public void setPeriodFrom(LocalDateFilter periodFrom) {
        this.periodFrom = periodFrom;
    }

    public LocalDateFilter getPeriodTo() {
        return periodTo;
    }

    public void setPeriodTo(LocalDateFilter periodTo) {
        this.periodTo = periodTo;
    }

    public BooleanFilter getUploadProcessed() {
        return uploadProcessed;
    }

    public void setUploadProcessed(BooleanFilter uploadProcessed) {
        this.uploadProcessed = uploadProcessed;
    }

    public BooleanFilter getUploadSuccessful() {
        return uploadSuccessful;
    }

    public void setUploadSuccessful(BooleanFilter uploadSuccessful) {
        this.uploadSuccessful = uploadSuccessful;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PrepaymentDataEntryFileCriteria that = (PrepaymentDataEntryFileCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(periodFrom, that.periodFrom) && Objects.equals(periodTo, that.periodTo) && Objects.equals(uploadProcessed, that.uploadProcessed) &&
            Objects.equals(uploadSuccessful, that.uploadSuccessful);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, periodFrom, periodTo, uploadProcessed, uploadSuccessful);
    }

    @Override
    public String toString() {
        return "PrepaymentDataEntryFileCriteria{" + (id != null ? "id=" + id + ", " : "") + (periodFrom != null ? "periodFrom=" + periodFrom + ", " : "") +
            (periodTo != null ? "periodTo=" + periodTo + ", " : "") + (uploadProcessed != null ? "uploadProcessed=" + uploadProcessed + ", " : "") +
            (uploadSuccessful != null ? "uploadSuccessful=" + uploadSuccessful + ", " : "") + "}";
    }

}
