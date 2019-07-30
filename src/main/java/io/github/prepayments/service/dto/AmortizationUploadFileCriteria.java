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
 * Criteria class for the {@link io.github.prepayments.domain.AmortizationUploadFile} entity. This class is used
 * in {@link io.github.prepayments.web.rest.AmortizationUploadFileResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /amortization-upload-files?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AmortizationUploadFileCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter periodFrom;

    private LocalDateFilter periodTo;

    private BooleanFilter uploadSuccessful;

    private BooleanFilter uploadProcessed;

    private IntegerFilter entriesCount;

    private StringFilter fileToken;

    public AmortizationUploadFileCriteria(){
    }

    public AmortizationUploadFileCriteria(AmortizationUploadFileCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.periodFrom = other.periodFrom == null ? null : other.periodFrom.copy();
        this.periodTo = other.periodTo == null ? null : other.periodTo.copy();
        this.uploadSuccessful = other.uploadSuccessful == null ? null : other.uploadSuccessful.copy();
        this.uploadProcessed = other.uploadProcessed == null ? null : other.uploadProcessed.copy();
        this.entriesCount = other.entriesCount == null ? null : other.entriesCount.copy();
        this.fileToken = other.fileToken == null ? null : other.fileToken.copy();
    }

    @Override
    public AmortizationUploadFileCriteria copy() {
        return new AmortizationUploadFileCriteria(this);
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

    public BooleanFilter getUploadSuccessful() {
        return uploadSuccessful;
    }

    public void setUploadSuccessful(BooleanFilter uploadSuccessful) {
        this.uploadSuccessful = uploadSuccessful;
    }

    public BooleanFilter getUploadProcessed() {
        return uploadProcessed;
    }

    public void setUploadProcessed(BooleanFilter uploadProcessed) {
        this.uploadProcessed = uploadProcessed;
    }

    public IntegerFilter getEntriesCount() {
        return entriesCount;
    }

    public void setEntriesCount(IntegerFilter entriesCount) {
        this.entriesCount = entriesCount;
    }

    public StringFilter getFileToken() {
        return fileToken;
    }

    public void setFileToken(StringFilter fileToken) {
        this.fileToken = fileToken;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AmortizationUploadFileCriteria that = (AmortizationUploadFileCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(periodFrom, that.periodFrom) &&
            Objects.equals(periodTo, that.periodTo) &&
            Objects.equals(uploadSuccessful, that.uploadSuccessful) &&
            Objects.equals(uploadProcessed, that.uploadProcessed) &&
            Objects.equals(entriesCount, that.entriesCount) &&
            Objects.equals(fileToken, that.fileToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        periodFrom,
        periodTo,
        uploadSuccessful,
        uploadProcessed,
        entriesCount,
        fileToken
        );
    }

    @Override
    public String toString() {
        return "AmortizationUploadFileCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (periodFrom != null ? "periodFrom=" + periodFrom + ", " : "") +
                (periodTo != null ? "periodTo=" + periodTo + ", " : "") +
                (uploadSuccessful != null ? "uploadSuccessful=" + uploadSuccessful + ", " : "") +
                (uploadProcessed != null ? "uploadProcessed=" + uploadProcessed + ", " : "") +
                (entriesCount != null ? "entriesCount=" + entriesCount + ", " : "") +
                (fileToken != null ? "fileToken=" + fileToken + ", " : "") +
            "}";
    }

}
