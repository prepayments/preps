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
 * Criteria class for the {@link io.github.prepayments.domain.AmortizationUpload} entity. This class is used
 * in {@link io.github.prepayments.web.rest.AmortizationUploadResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /amortization-uploads?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AmortizationUploadCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter accountName;

    private StringFilter particulars;

    private StringFilter amortizationServiceOutletCode;

    private StringFilter prepaymentServiceOutletCode;

    private StringFilter prepaymentAccountNumber;

    private StringFilter expenseAccountNumber;

    private StringFilter prepaymentTransactionId;

    private LocalDateFilter prepaymentTransactionDate;

    private BigDecimalFilter prepaymentTransactionAmount;

    private BigDecimalFilter amortizationAmount;

    private IntegerFilter numberOfAmortizations;

    private LocalDateFilter firstAmortizationDate;

    private BooleanFilter uploadSuccessful;

    private BooleanFilter uploadOrphaned;

    private StringFilter OriginatingFileToken;

    public AmortizationUploadCriteria(){
    }

    public AmortizationUploadCriteria(AmortizationUploadCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.accountName = other.accountName == null ? null : other.accountName.copy();
        this.particulars = other.particulars == null ? null : other.particulars.copy();
        this.amortizationServiceOutletCode = other.amortizationServiceOutletCode == null ? null : other.amortizationServiceOutletCode.copy();
        this.prepaymentServiceOutletCode = other.prepaymentServiceOutletCode == null ? null : other.prepaymentServiceOutletCode.copy();
        this.prepaymentAccountNumber = other.prepaymentAccountNumber == null ? null : other.prepaymentAccountNumber.copy();
        this.expenseAccountNumber = other.expenseAccountNumber == null ? null : other.expenseAccountNumber.copy();
        this.prepaymentTransactionId = other.prepaymentTransactionId == null ? null : other.prepaymentTransactionId.copy();
        this.prepaymentTransactionDate = other.prepaymentTransactionDate == null ? null : other.prepaymentTransactionDate.copy();
        this.prepaymentTransactionAmount = other.prepaymentTransactionAmount == null ? null : other.prepaymentTransactionAmount.copy();
        this.amortizationAmount = other.amortizationAmount == null ? null : other.amortizationAmount.copy();
        this.numberOfAmortizations = other.numberOfAmortizations == null ? null : other.numberOfAmortizations.copy();
        this.firstAmortizationDate = other.firstAmortizationDate == null ? null : other.firstAmortizationDate.copy();
        this.uploadSuccessful = other.uploadSuccessful == null ? null : other.uploadSuccessful.copy();
        this.uploadOrphaned = other.uploadOrphaned == null ? null : other.uploadOrphaned.copy();
        this.OriginatingFileToken = other.OriginatingFileToken == null ? null : other.OriginatingFileToken.copy();
    }

    @Override
    public AmortizationUploadCriteria copy() {
        return new AmortizationUploadCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAccountName() {
        return accountName;
    }

    public void setAccountName(StringFilter accountName) {
        this.accountName = accountName;
    }

    public StringFilter getParticulars() {
        return particulars;
    }

    public void setParticulars(StringFilter particulars) {
        this.particulars = particulars;
    }

    public StringFilter getAmortizationServiceOutletCode() {
        return amortizationServiceOutletCode;
    }

    public void setAmortizationServiceOutletCode(StringFilter amortizationServiceOutletCode) {
        this.amortizationServiceOutletCode = amortizationServiceOutletCode;
    }

    public StringFilter getPrepaymentServiceOutletCode() {
        return prepaymentServiceOutletCode;
    }

    public void setPrepaymentServiceOutletCode(StringFilter prepaymentServiceOutletCode) {
        this.prepaymentServiceOutletCode = prepaymentServiceOutletCode;
    }

    public StringFilter getPrepaymentAccountNumber() {
        return prepaymentAccountNumber;
    }

    public void setPrepaymentAccountNumber(StringFilter prepaymentAccountNumber) {
        this.prepaymentAccountNumber = prepaymentAccountNumber;
    }

    public StringFilter getExpenseAccountNumber() {
        return expenseAccountNumber;
    }

    public void setExpenseAccountNumber(StringFilter expenseAccountNumber) {
        this.expenseAccountNumber = expenseAccountNumber;
    }

    public StringFilter getPrepaymentTransactionId() {
        return prepaymentTransactionId;
    }

    public void setPrepaymentTransactionId(StringFilter prepaymentTransactionId) {
        this.prepaymentTransactionId = prepaymentTransactionId;
    }

    public LocalDateFilter getPrepaymentTransactionDate() {
        return prepaymentTransactionDate;
    }

    public void setPrepaymentTransactionDate(LocalDateFilter prepaymentTransactionDate) {
        this.prepaymentTransactionDate = prepaymentTransactionDate;
    }

    public BigDecimalFilter getPrepaymentTransactionAmount() {
        return prepaymentTransactionAmount;
    }

    public void setPrepaymentTransactionAmount(BigDecimalFilter prepaymentTransactionAmount) {
        this.prepaymentTransactionAmount = prepaymentTransactionAmount;
    }

    public BigDecimalFilter getAmortizationAmount() {
        return amortizationAmount;
    }

    public void setAmortizationAmount(BigDecimalFilter amortizationAmount) {
        this.amortizationAmount = amortizationAmount;
    }

    public IntegerFilter getNumberOfAmortizations() {
        return numberOfAmortizations;
    }

    public void setNumberOfAmortizations(IntegerFilter numberOfAmortizations) {
        this.numberOfAmortizations = numberOfAmortizations;
    }

    public LocalDateFilter getFirstAmortizationDate() {
        return firstAmortizationDate;
    }

    public void setFirstAmortizationDate(LocalDateFilter firstAmortizationDate) {
        this.firstAmortizationDate = firstAmortizationDate;
    }

    public BooleanFilter getUploadSuccessful() {
        return uploadSuccessful;
    }

    public void setUploadSuccessful(BooleanFilter uploadSuccessful) {
        this.uploadSuccessful = uploadSuccessful;
    }

    public BooleanFilter getUploadOrphaned() {
        return uploadOrphaned;
    }

    public void setUploadOrphaned(BooleanFilter uploadOrphaned) {
        this.uploadOrphaned = uploadOrphaned;
    }

    public StringFilter getOriginatingFileToken() {
        return OriginatingFileToken;
    }

    public void setOriginatingFileToken(StringFilter OriginatingFileToken) {
        this.OriginatingFileToken = OriginatingFileToken;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AmortizationUploadCriteria that = (AmortizationUploadCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(accountName, that.accountName) &&
            Objects.equals(particulars, that.particulars) &&
            Objects.equals(amortizationServiceOutletCode, that.amortizationServiceOutletCode) &&
            Objects.equals(prepaymentServiceOutletCode, that.prepaymentServiceOutletCode) &&
            Objects.equals(prepaymentAccountNumber, that.prepaymentAccountNumber) &&
            Objects.equals(expenseAccountNumber, that.expenseAccountNumber) &&
            Objects.equals(prepaymentTransactionId, that.prepaymentTransactionId) &&
            Objects.equals(prepaymentTransactionDate, that.prepaymentTransactionDate) &&
            Objects.equals(prepaymentTransactionAmount, that.prepaymentTransactionAmount) &&
            Objects.equals(amortizationAmount, that.amortizationAmount) &&
            Objects.equals(numberOfAmortizations, that.numberOfAmortizations) &&
            Objects.equals(firstAmortizationDate, that.firstAmortizationDate) &&
            Objects.equals(uploadSuccessful, that.uploadSuccessful) &&
            Objects.equals(uploadOrphaned, that.uploadOrphaned) &&
            Objects.equals(OriginatingFileToken, that.OriginatingFileToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        accountName,
        particulars,
        amortizationServiceOutletCode,
        prepaymentServiceOutletCode,
        prepaymentAccountNumber,
        expenseAccountNumber,
        prepaymentTransactionId,
        prepaymentTransactionDate,
        prepaymentTransactionAmount,
        amortizationAmount,
        numberOfAmortizations,
        firstAmortizationDate,
        uploadSuccessful,
        uploadOrphaned,
        OriginatingFileToken
        );
    }

    @Override
    public String toString() {
        return "AmortizationUploadCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (accountName != null ? "accountName=" + accountName + ", " : "") +
                (particulars != null ? "particulars=" + particulars + ", " : "") +
                (amortizationServiceOutletCode != null ? "amortizationServiceOutletCode=" + amortizationServiceOutletCode + ", " : "") +
                (prepaymentServiceOutletCode != null ? "prepaymentServiceOutletCode=" + prepaymentServiceOutletCode + ", " : "") +
                (prepaymentAccountNumber != null ? "prepaymentAccountNumber=" + prepaymentAccountNumber + ", " : "") +
                (expenseAccountNumber != null ? "expenseAccountNumber=" + expenseAccountNumber + ", " : "") +
                (prepaymentTransactionId != null ? "prepaymentTransactionId=" + prepaymentTransactionId + ", " : "") +
                (prepaymentTransactionDate != null ? "prepaymentTransactionDate=" + prepaymentTransactionDate + ", " : "") +
                (prepaymentTransactionAmount != null ? "prepaymentTransactionAmount=" + prepaymentTransactionAmount + ", " : "") +
                (amortizationAmount != null ? "amortizationAmount=" + amortizationAmount + ", " : "") +
                (numberOfAmortizations != null ? "numberOfAmortizations=" + numberOfAmortizations + ", " : "") +
                (firstAmortizationDate != null ? "firstAmortizationDate=" + firstAmortizationDate + ", " : "") +
                (uploadSuccessful != null ? "uploadSuccessful=" + uploadSuccessful + ", " : "") +
                (uploadOrphaned != null ? "uploadOrphaned=" + uploadOrphaned + ", " : "") +
                (OriginatingFileToken != null ? "OriginatingFileToken=" + OriginatingFileToken + ", " : "") +
            "}";
    }

}
