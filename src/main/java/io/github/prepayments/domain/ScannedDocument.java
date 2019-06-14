package io.github.prepayments.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A ScannedDocument.
 */
@Entity
@Table(name = "scanned_document")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "scanneddocument")
public class ScannedDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "document_name", nullable = false, unique = true)
    private String documentName;

    @Column(name = "description")
    private String description;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @Lob
    @Column(name = "invoice_document")
    private byte[] invoiceDocument;

    @Column(name = "invoice_document_content_type")
    private String invoiceDocumentContentType;

    @Lob
    @Column(name = "requisition_document")
    private byte[] requisitionDocument;

    @Column(name = "requisition_document_content_type")
    private String requisitionDocumentContentType;

    @Lob
    @Column(name = "approval_memo_document")
    private byte[] approvalMemoDocument;

    @Column(name = "approval_memo_document_content_type")
    private String approvalMemoDocumentContentType;

    @Lob
    @Column(name = "other_scanned_document")
    private byte[] otherScannedDocument;

    @Column(name = "other_scanned_document_content_type")
    private String otherScannedDocumentContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public ScannedDocument documentName(String documentName) {
        this.documentName = documentName;
        return this;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDescription() {
        return description;
    }

    public ScannedDocument description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public ScannedDocument invoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
        return this;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public ScannedDocument transactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public ScannedDocument transactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
        return this;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public byte[] getInvoiceDocument() {
        return invoiceDocument;
    }

    public ScannedDocument invoiceDocument(byte[] invoiceDocument) {
        this.invoiceDocument = invoiceDocument;
        return this;
    }

    public void setInvoiceDocument(byte[] invoiceDocument) {
        this.invoiceDocument = invoiceDocument;
    }

    public String getInvoiceDocumentContentType() {
        return invoiceDocumentContentType;
    }

    public ScannedDocument invoiceDocumentContentType(String invoiceDocumentContentType) {
        this.invoiceDocumentContentType = invoiceDocumentContentType;
        return this;
    }

    public void setInvoiceDocumentContentType(String invoiceDocumentContentType) {
        this.invoiceDocumentContentType = invoiceDocumentContentType;
    }

    public byte[] getRequisitionDocument() {
        return requisitionDocument;
    }

    public ScannedDocument requisitionDocument(byte[] requisitionDocument) {
        this.requisitionDocument = requisitionDocument;
        return this;
    }

    public void setRequisitionDocument(byte[] requisitionDocument) {
        this.requisitionDocument = requisitionDocument;
    }

    public String getRequisitionDocumentContentType() {
        return requisitionDocumentContentType;
    }

    public ScannedDocument requisitionDocumentContentType(String requisitionDocumentContentType) {
        this.requisitionDocumentContentType = requisitionDocumentContentType;
        return this;
    }

    public void setRequisitionDocumentContentType(String requisitionDocumentContentType) {
        this.requisitionDocumentContentType = requisitionDocumentContentType;
    }

    public byte[] getApprovalMemoDocument() {
        return approvalMemoDocument;
    }

    public ScannedDocument approvalMemoDocument(byte[] approvalMemoDocument) {
        this.approvalMemoDocument = approvalMemoDocument;
        return this;
    }

    public void setApprovalMemoDocument(byte[] approvalMemoDocument) {
        this.approvalMemoDocument = approvalMemoDocument;
    }

    public String getApprovalMemoDocumentContentType() {
        return approvalMemoDocumentContentType;
    }

    public ScannedDocument approvalMemoDocumentContentType(String approvalMemoDocumentContentType) {
        this.approvalMemoDocumentContentType = approvalMemoDocumentContentType;
        return this;
    }

    public void setApprovalMemoDocumentContentType(String approvalMemoDocumentContentType) {
        this.approvalMemoDocumentContentType = approvalMemoDocumentContentType;
    }

    public byte[] getOtherScannedDocument() {
        return otherScannedDocument;
    }

    public ScannedDocument otherScannedDocument(byte[] otherScannedDocument) {
        this.otherScannedDocument = otherScannedDocument;
        return this;
    }

    public void setOtherScannedDocument(byte[] otherScannedDocument) {
        this.otherScannedDocument = otherScannedDocument;
    }

    public String getOtherScannedDocumentContentType() {
        return otherScannedDocumentContentType;
    }

    public ScannedDocument otherScannedDocumentContentType(String otherScannedDocumentContentType) {
        this.otherScannedDocumentContentType = otherScannedDocumentContentType;
        return this;
    }

    public void setOtherScannedDocumentContentType(String otherScannedDocumentContentType) {
        this.otherScannedDocumentContentType = otherScannedDocumentContentType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScannedDocument)) {
            return false;
        }
        return id != null && id.equals(((ScannedDocument) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ScannedDocument{" +
            "id=" + getId() +
            ", documentName='" + getDocumentName() + "'" +
            ", description='" + getDescription() + "'" +
            ", invoiceNumber='" + getInvoiceNumber() + "'" +
            ", transactionId='" + getTransactionId() + "'" +
            ", transactionDate='" + getTransactionDate() + "'" +
            ", invoiceDocument='" + getInvoiceDocument() + "'" +
            ", invoiceDocumentContentType='" + getInvoiceDocumentContentType() + "'" +
            ", requisitionDocument='" + getRequisitionDocument() + "'" +
            ", requisitionDocumentContentType='" + getRequisitionDocumentContentType() + "'" +
            ", approvalMemoDocument='" + getApprovalMemoDocument() + "'" +
            ", approvalMemoDocumentContentType='" + getApprovalMemoDocumentContentType() + "'" +
            ", otherScannedDocument='" + getOtherScannedDocument() + "'" +
            ", otherScannedDocumentContentType='" + getOtherScannedDocumentContentType() + "'" +
            "}";
    }
}
