package io.github.prepayments.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link io.github.prepayments.domain.PrepaymentEntry} entity. This class is used in {@link io.github.prepayments.web.rest.PrepaymentEntryResource} to receive all the possible
 * filtering options from the Http GET request parameters. For example the following could be a valid request: {@code /prepayment-entries?id
 * .greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use fix type specific filters.
 */
public class PrepaymentEntryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter accountNumber;

    private StringFilter accountName;

    private StringFilter prepaymentId;

    private LocalDateFilter prepaymentDate;

    private StringFilter particulars;

    private StringFilter serviceOutlet;

    private BigDecimalFilter prepaymentAmount;

    private IntegerFilter months;

    private StringFilter supplierName;

    private StringFilter invoiceNumber;

    private LongFilter scannedDocumentId;

    private LongFilter amortizationEntryId;

    public PrepaymentEntryCriteria() {
    }

    public PrepaymentEntryCriteria(PrepaymentEntryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.accountNumber = other.accountNumber == null ? null : other.accountNumber.copy();
        this.accountName = other.accountName == null ? null : other.accountName.copy();
        this.prepaymentId = other.prepaymentId == null ? null : other.prepaymentId.copy();
        this.prepaymentDate = other.prepaymentDate == null ? null : other.prepaymentDate.copy();
        this.particulars = other.particulars == null ? null : other.particulars.copy();
        this.serviceOutlet = other.serviceOutlet == null ? null : other.serviceOutlet.copy();
        this.prepaymentAmount = other.prepaymentAmount == null ? null : other.prepaymentAmount.copy();
        this.months = other.months == null ? null : other.months.copy();
        this.supplierName = other.supplierName == null ? null : other.supplierName.copy();
        this.invoiceNumber = other.invoiceNumber == null ? null : other.invoiceNumber.copy();
        this.scannedDocumentId = other.scannedDocumentId == null ? null : other.scannedDocumentId.copy();
        this.amortizationEntryId = other.amortizationEntryId == null ? null : other.amortizationEntryId.copy();
    }

    @Override
    public PrepaymentEntryCriteria copy() {
        return new PrepaymentEntryCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(StringFilter accountNumber) {
        this.accountNumber = accountNumber;
    }

    public StringFilter getAccountName() {
        return accountName;
    }

    public void setAccountName(StringFilter accountName) {
        this.accountName = accountName;
    }

    public StringFilter getPrepaymentId() {
        return prepaymentId;
    }

    public void setPrepaymentId(StringFilter prepaymentId) {
        this.prepaymentId = prepaymentId;
    }

    public LocalDateFilter getPrepaymentDate() {
        return prepaymentDate;
    }

    public void setPrepaymentDate(LocalDateFilter prepaymentDate) {
        this.prepaymentDate = prepaymentDate;
    }

    public StringFilter getParticulars() {
        return particulars;
    }

    public void setParticulars(StringFilter particulars) {
        this.particulars = particulars;
    }

    public StringFilter getServiceOutlet() {
        return serviceOutlet;
    }

    public void setServiceOutlet(StringFilter serviceOutlet) {
        this.serviceOutlet = serviceOutlet;
    }

    public BigDecimalFilter getPrepaymentAmount() {
        return prepaymentAmount;
    }

    public void setPrepaymentAmount(BigDecimalFilter prepaymentAmount) {
        this.prepaymentAmount = prepaymentAmount;
    }

    public IntegerFilter getMonths() {
        return months;
    }

    public void setMonths(IntegerFilter months) {
        this.months = months;
    }

    public StringFilter getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(StringFilter supplierName) {
        this.supplierName = supplierName;
    }

    public StringFilter getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(StringFilter invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public LongFilter getScannedDocumentId() {
        return scannedDocumentId;
    }

    public void setScannedDocumentId(LongFilter scannedDocumentId) {
        this.scannedDocumentId = scannedDocumentId;
    }

    public LongFilter getAmortizationEntryId() {
        return amortizationEntryId;
    }

    public void setAmortizationEntryId(LongFilter amortizationEntryId) {
        this.amortizationEntryId = amortizationEntryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PrepaymentEntryCriteria that = (PrepaymentEntryCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(accountNumber, that.accountNumber) && Objects.equals(accountName, that.accountName) && Objects.equals(prepaymentId, that.prepaymentId) &&
            Objects.equals(prepaymentDate, that.prepaymentDate) && Objects.equals(particulars, that.particulars) && Objects.equals(serviceOutlet, that.serviceOutlet) &&
            Objects.equals(prepaymentAmount, that.prepaymentAmount) && Objects.equals(months, that.months) && Objects.equals(supplierName, that.supplierName) &&
            Objects.equals(invoiceNumber, that.invoiceNumber) && Objects.equals(scannedDocumentId, that.scannedDocumentId) && Objects.equals(amortizationEntryId, that.amortizationEntryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountNumber, accountName, prepaymentId, prepaymentDate, particulars, serviceOutlet, prepaymentAmount, months, supplierName, invoiceNumber, scannedDocumentId,
                            amortizationEntryId);
    }

    @Override
    public String toString() {
        return "PrepaymentEntryCriteria{" + (id != null ? "id=" + id + ", " : "") + (accountNumber != null ? "accountNumber=" + accountNumber + ", " : "") +
            (accountName != null ? "accountName=" + accountName + ", " : "") + (prepaymentId != null ? "prepaymentId=" + prepaymentId + ", " : "") +
            (prepaymentDate != null ? "prepaymentDate=" + prepaymentDate + ", " : "") + (particulars != null ? "particulars=" + particulars + ", " : "") +
            (serviceOutlet != null ? "serviceOutlet=" + serviceOutlet + ", " : "") + (prepaymentAmount != null ? "prepaymentAmount=" + prepaymentAmount + ", " : "") +
            (months != null ? "months=" + months + ", " : "") + (supplierName != null ? "supplierName=" + supplierName + ", " : "") +
            (invoiceNumber != null ? "invoiceNumber=" + invoiceNumber + ", " : "") + (scannedDocumentId != null ? "scannedDocumentId=" + scannedDocumentId + ", " : "") +
            (amortizationEntryId != null ? "amortizationEntryId=" + amortizationEntryId + ", " : "") + "}";
    }

}
