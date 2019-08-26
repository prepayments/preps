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
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link io.github.prepayments.domain.AmortizationEntry} entity. This class is used
 * in {@link io.github.prepayments.web.rest.AmortizationEntryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /amortization-entries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AmortizationEntryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter amortizationDate;

    private BigDecimalFilter amortizationAmount;

    private StringFilter particulars;

    private StringFilter prepaymentServiceOutlet;

    private StringFilter prepaymentAccountNumber;

    private StringFilter amortizationServiceOutlet;

    private StringFilter amortizationAccountNumber;

    private StringFilter originatingFileToken;

    private StringFilter amortizationTag;

    private BooleanFilter orphaned;

    private LongFilter prepaymentEntryId;

    public AmortizationEntryCriteria(){
    }

    public AmortizationEntryCriteria(AmortizationEntryCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.amortizationDate = other.amortizationDate == null ? null : other.amortizationDate.copy();
        this.amortizationAmount = other.amortizationAmount == null ? null : other.amortizationAmount.copy();
        this.particulars = other.particulars == null ? null : other.particulars.copy();
        this.prepaymentServiceOutlet = other.prepaymentServiceOutlet == null ? null : other.prepaymentServiceOutlet.copy();
        this.prepaymentAccountNumber = other.prepaymentAccountNumber == null ? null : other.prepaymentAccountNumber.copy();
        this.amortizationServiceOutlet = other.amortizationServiceOutlet == null ? null : other.amortizationServiceOutlet.copy();
        this.amortizationAccountNumber = other.amortizationAccountNumber == null ? null : other.amortizationAccountNumber.copy();
        this.originatingFileToken = other.originatingFileToken == null ? null : other.originatingFileToken.copy();
        this.amortizationTag = other.amortizationTag == null ? null : other.amortizationTag.copy();
        this.orphaned = other.orphaned == null ? null : other.orphaned.copy();
        this.prepaymentEntryId = other.prepaymentEntryId == null ? null : other.prepaymentEntryId.copy();
    }

    @Override
    public AmortizationEntryCriteria copy() {
        return new AmortizationEntryCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getAmortizationDate() {
        return amortizationDate;
    }

    public void setAmortizationDate(LocalDateFilter amortizationDate) {
        this.amortizationDate = amortizationDate;
    }

    public BigDecimalFilter getAmortizationAmount() {
        return amortizationAmount;
    }

    public void setAmortizationAmount(BigDecimalFilter amortizationAmount) {
        this.amortizationAmount = amortizationAmount;
    }

    public StringFilter getParticulars() {
        return particulars;
    }

    public void setParticulars(StringFilter particulars) {
        this.particulars = particulars;
    }

    public StringFilter getPrepaymentServiceOutlet() {
        return prepaymentServiceOutlet;
    }

    public void setPrepaymentServiceOutlet(StringFilter prepaymentServiceOutlet) {
        this.prepaymentServiceOutlet = prepaymentServiceOutlet;
    }

    public StringFilter getPrepaymentAccountNumber() {
        return prepaymentAccountNumber;
    }

    public void setPrepaymentAccountNumber(StringFilter prepaymentAccountNumber) {
        this.prepaymentAccountNumber = prepaymentAccountNumber;
    }

    public StringFilter getAmortizationServiceOutlet() {
        return amortizationServiceOutlet;
    }

    public void setAmortizationServiceOutlet(StringFilter amortizationServiceOutlet) {
        this.amortizationServiceOutlet = amortizationServiceOutlet;
    }

    public StringFilter getAmortizationAccountNumber() {
        return amortizationAccountNumber;
    }

    public void setAmortizationAccountNumber(StringFilter amortizationAccountNumber) {
        this.amortizationAccountNumber = amortizationAccountNumber;
    }

    public StringFilter getOriginatingFileToken() {
        return originatingFileToken;
    }

    public void setOriginatingFileToken(StringFilter originatingFileToken) {
        this.originatingFileToken = originatingFileToken;
    }

    public StringFilter getAmortizationTag() {
        return amortizationTag;
    }

    public void setAmortizationTag(StringFilter amortizationTag) {
        this.amortizationTag = amortizationTag;
    }

    public BooleanFilter getOrphaned() {
        return orphaned;
    }

    public void setOrphaned(BooleanFilter orphaned) {
        this.orphaned = orphaned;
    }

    public LongFilter getPrepaymentEntryId() {
        return prepaymentEntryId;
    }

    public void setPrepaymentEntryId(LongFilter prepaymentEntryId) {
        this.prepaymentEntryId = prepaymentEntryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AmortizationEntryCriteria that = (AmortizationEntryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(amortizationDate, that.amortizationDate) &&
            Objects.equals(amortizationAmount, that.amortizationAmount) &&
            Objects.equals(particulars, that.particulars) &&
            Objects.equals(prepaymentServiceOutlet, that.prepaymentServiceOutlet) &&
            Objects.equals(prepaymentAccountNumber, that.prepaymentAccountNumber) &&
            Objects.equals(amortizationServiceOutlet, that.amortizationServiceOutlet) &&
            Objects.equals(amortizationAccountNumber, that.amortizationAccountNumber) &&
            Objects.equals(originatingFileToken, that.originatingFileToken) &&
            Objects.equals(amortizationTag, that.amortizationTag) &&
            Objects.equals(orphaned, that.orphaned) &&
            Objects.equals(prepaymentEntryId, that.prepaymentEntryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        amortizationDate,
        amortizationAmount,
        particulars,
        prepaymentServiceOutlet,
        prepaymentAccountNumber,
        amortizationServiceOutlet,
        amortizationAccountNumber,
        originatingFileToken,
        amortizationTag,
        orphaned,
        prepaymentEntryId
        );
    }

    @Override
    public String toString() {
        return "AmortizationEntryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (amortizationDate != null ? "amortizationDate=" + amortizationDate + ", " : "") +
                (amortizationAmount != null ? "amortizationAmount=" + amortizationAmount + ", " : "") +
                (particulars != null ? "particulars=" + particulars + ", " : "") +
                (prepaymentServiceOutlet != null ? "prepaymentServiceOutlet=" + prepaymentServiceOutlet + ", " : "") +
                (prepaymentAccountNumber != null ? "prepaymentAccountNumber=" + prepaymentAccountNumber + ", " : "") +
                (amortizationServiceOutlet != null ? "amortizationServiceOutlet=" + amortizationServiceOutlet + ", " : "") +
                (amortizationAccountNumber != null ? "amortizationAccountNumber=" + amortizationAccountNumber + ", " : "") +
                (originatingFileToken != null ? "originatingFileToken=" + originatingFileToken + ", " : "") +
                (amortizationTag != null ? "amortizationTag=" + amortizationTag + ", " : "") +
                (orphaned != null ? "orphaned=" + orphaned + ", " : "") +
                (prepaymentEntryId != null ? "prepaymentEntryId=" + prepaymentEntryId + ", " : "") +
            "}";
    }

}
