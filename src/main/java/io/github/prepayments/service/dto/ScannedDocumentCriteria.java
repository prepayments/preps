package io.github.prepayments.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link io.github.prepayments.domain.ScannedDocument} entity. This class is used in {@link io.github.prepayments.web.rest.ScannedDocumentResource} to receive all the possible
 * filtering options from the Http GET request parameters. For example the following could be a valid request: {@code /scanned-documents?id
 * .greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use fix type specific filters.
 */
public class ScannedDocumentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter documentName;

    private StringFilter description;

    private StringFilter invoiceNumber;

    private StringFilter transactionId;

    private LocalDateFilter transactionDate;

    public ScannedDocumentCriteria() {
    }

    public ScannedDocumentCriteria(ScannedDocumentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.documentName = other.documentName == null ? null : other.documentName.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.invoiceNumber = other.invoiceNumber == null ? null : other.invoiceNumber.copy();
        this.transactionId = other.transactionId == null ? null : other.transactionId.copy();
        this.transactionDate = other.transactionDate == null ? null : other.transactionDate.copy();
    }

    @Override
    public ScannedDocumentCriteria copy() {
        return new ScannedDocumentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDocumentName() {
        return documentName;
    }

    public void setDocumentName(StringFilter documentName) {
        this.documentName = documentName;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(StringFilter invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public StringFilter getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(StringFilter transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDateFilter getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateFilter transactionDate) {
        this.transactionDate = transactionDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ScannedDocumentCriteria that = (ScannedDocumentCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(documentName, that.documentName) && Objects.equals(description, that.description) && Objects.equals(invoiceNumber, that.invoiceNumber) &&
            Objects.equals(transactionId, that.transactionId) && Objects.equals(transactionDate, that.transactionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, documentName, description, invoiceNumber, transactionId, transactionDate);
    }

    @Override
    public String toString() {
        return "ScannedDocumentCriteria{" + (id != null ? "id=" + id + ", " : "") + (documentName != null ? "documentName=" + documentName + ", " : "") +
            (description != null ? "description=" + description + ", " : "") + (invoiceNumber != null ? "invoiceNumber=" + invoiceNumber + ", " : "") +
            (transactionId != null ? "transactionId=" + transactionId + ", " : "") + (transactionDate != null ? "transactionDate=" + transactionDate + ", " : "") + "}";
    }

}
