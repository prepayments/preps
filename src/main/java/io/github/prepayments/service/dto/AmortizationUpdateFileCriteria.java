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

/**
 * Criteria class for the {@link io.github.prepayments.domain.AmortizationUpdateFile} entity. This class is used
 * in {@link io.github.prepayments.web.rest.AmortizationUpdateFileResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /amortization-update-files?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AmortizationUpdateFileCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter narration;

    private BooleanFilter uploadSuccessful;

    private BooleanFilter uploadProcessed;

    private IntegerFilter entriesCount;

    private StringFilter fileToken;

    private StringFilter reasonForUpdate;

    public AmortizationUpdateFileCriteria(){
    }

    public AmortizationUpdateFileCriteria(AmortizationUpdateFileCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.narration = other.narration == null ? null : other.narration.copy();
        this.uploadSuccessful = other.uploadSuccessful == null ? null : other.uploadSuccessful.copy();
        this.uploadProcessed = other.uploadProcessed == null ? null : other.uploadProcessed.copy();
        this.entriesCount = other.entriesCount == null ? null : other.entriesCount.copy();
        this.fileToken = other.fileToken == null ? null : other.fileToken.copy();
        this.reasonForUpdate = other.reasonForUpdate == null ? null : other.reasonForUpdate.copy();
    }

    @Override
    public AmortizationUpdateFileCriteria copy() {
        return new AmortizationUpdateFileCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNarration() {
        return narration;
    }

    public void setNarration(StringFilter narration) {
        this.narration = narration;
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

    public StringFilter getReasonForUpdate() {
        return reasonForUpdate;
    }

    public void setReasonForUpdate(StringFilter reasonForUpdate) {
        this.reasonForUpdate = reasonForUpdate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AmortizationUpdateFileCriteria that = (AmortizationUpdateFileCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(narration, that.narration) &&
            Objects.equals(uploadSuccessful, that.uploadSuccessful) &&
            Objects.equals(uploadProcessed, that.uploadProcessed) &&
            Objects.equals(entriesCount, that.entriesCount) &&
            Objects.equals(fileToken, that.fileToken) &&
            Objects.equals(reasonForUpdate, that.reasonForUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        narration,
        uploadSuccessful,
        uploadProcessed,
        entriesCount,
        fileToken,
        reasonForUpdate
        );
    }

    @Override
    public String toString() {
        return "AmortizationUpdateFileCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (narration != null ? "narration=" + narration + ", " : "") +
                (uploadSuccessful != null ? "uploadSuccessful=" + uploadSuccessful + ", " : "") +
                (uploadProcessed != null ? "uploadProcessed=" + uploadProcessed + ", " : "") +
                (entriesCount != null ? "entriesCount=" + entriesCount + ", " : "") +
                (fileToken != null ? "fileToken=" + fileToken + ", " : "") +
                (reasonForUpdate != null ? "reasonForUpdate=" + reasonForUpdate + ", " : "") +
            "}";
    }

}
