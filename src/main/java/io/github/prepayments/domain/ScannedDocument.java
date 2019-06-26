package io.github.prepayments.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

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

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public ScannedDocument documentName(String documentName) {
        this.documentName = documentName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ScannedDocument description(String description) {
        this.description = description;
        return this;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public ScannedDocument invoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
        return this;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public ScannedDocument transactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public ScannedDocument transactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
        return this;
    }

    public byte[] getInvoiceDocument() {
        return invoiceDocument;
    }

    public void setInvoiceDocument(byte[] invoiceDocument) {
        this.invoiceDocument = invoiceDocument;
    }

    public ScannedDocument invoiceDocument(byte[] invoiceDocument) {
        this.invoiceDocument = invoiceDocument;
        return this;
    }

    public String getInvoiceDocumentContentType() {
        return invoiceDocumentContentType;
    }

    public void setInvoiceDocumentContentType(String invoiceDocumentContentType) {
        this.invoiceDocumentContentType = invoiceDocumentContentType;
    }

    public ScannedDocument invoiceDocumentContentType(String invoiceDocumentContentType) {
        this.invoiceDocumentContentType = invoiceDocumentContentType;
        return this;
    }

    public byte[] getRequisitionDocument() {
        return requisitionDocument;
    }

    public void setRequisitionDocument(byte[] requisitionDocument) {
        this.requisitionDocument = requisitionDocument;
    }

    public ScannedDocument requisitionDocument(byte[] requisitionDocument) {
        this.requisitionDocument = requisitionDocument;
        return this;
    }

    public String getRequisitionDocumentContentType() {
        return requisitionDocumentContentType;
    }

    public void setRequisitionDocumentContentType(String requisitionDocumentContentType) {
        this.requisitionDocumentContentType = requisitionDocumentContentType;
    }

    public ScannedDocument requisitionDocumentContentType(String requisitionDocumentContentType) {
        this.requisitionDocumentContentType = requisitionDocumentContentType;
        return this;
    }

    public byte[] getApprovalMemoDocument() {
        return approvalMemoDocument;
    }

    public void setApprovalMemoDocument(byte[] approvalMemoDocument) {
        this.approvalMemoDocument = approvalMemoDocument;
    }

    public ScannedDocument approvalMemoDocument(byte[] approvalMemoDocument) {
        this.approvalMemoDocument = approvalMemoDocument;
        return this;
    }

    public String getApprovalMemoDocumentContentType() {
        return approvalMemoDocumentContentType;
    }

    public void setApprovalMemoDocumentContentType(String approvalMemoDocumentContentType) {
        this.approvalMemoDocumentContentType = approvalMemoDocumentContentType;
    }

    public ScannedDocument approvalMemoDocumentContentType(String approvalMemoDocumentContentType) {
        this.approvalMemoDocumentContentType = approvalMemoDocumentContentType;
        return this;
    }

    public byte[] getOtherScannedDocument() {
        return otherScannedDocument;
    }

    public void setOtherScannedDocument(byte[] otherScannedDocument) {
        this.otherScannedDocument = otherScannedDocument;
    }

    public ScannedDocument otherScannedDocument(byte[] otherScannedDocument) {
        this.otherScannedDocument = otherScannedDocument;
        return this;
    }

    public String getOtherScannedDocumentContentType() {
        return otherScannedDocumentContentType;
    }

    public void setOtherScannedDocumentContentType(String otherScannedDocumentContentType) {
        this.otherScannedDocumentContentType = otherScannedDocumentContentType;
    }

    public ScannedDocument otherScannedDocumentContentType(String otherScannedDocumentContentType) {
        this.otherScannedDocumentContentType = otherScannedDocumentContentType;
        return this;
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
        return "ScannedDocument{" + "id=" + getId() + ", documentName='" + getDocumentName() + "'" + ", description='" + getDescription() + "'" + ", invoiceNumber='" + getInvoiceNumber() + "'" +
            ", transactionId='" + getTransactionId() + "'" + ", transactionDate='" + getTransactionDate() + "'" + ", invoiceDocument='" + getInvoiceDocument() + "'" +
            ", invoiceDocumentContentType='" + getInvoiceDocumentContentType() + "'" + ", requisitionDocument='" + getRequisitionDocument() + "'" + ", requisitionDocumentContentType='" +
            getRequisitionDocumentContentType() + "'" + ", approvalMemoDocument='" + getApprovalMemoDocument() + "'" + ", approvalMemoDocumentContentType='" + getApprovalMemoDocumentContentType() +
            "'" + ", otherScannedDocument='" + getOtherScannedDocument() + "'" + ", otherScannedDocumentContentType='" + getOtherScannedDocumentContentType() + "'" + "}";
    }
}
