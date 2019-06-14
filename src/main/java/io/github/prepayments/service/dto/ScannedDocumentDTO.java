package io.github.prepayments.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link io.github.prepayments.domain.ScannedDocument} entity.
 */
public class ScannedDocumentDTO implements Serializable {

    private Long id;

    @NotNull
    private String documentName;

    private String description;

    private String invoiceNumber;

    private String transactionId;

    private LocalDate transactionDate;

    @Lob
    private byte[] invoiceDocument;

    private String invoiceDocumentContentType;
    @Lob
    private byte[] requisitionDocument;

    private String requisitionDocumentContentType;
    @Lob
    private byte[] approvalMemoDocument;

    private String approvalMemoDocumentContentType;
    @Lob
    private byte[] otherScannedDocument;

    private String otherScannedDocumentContentType;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public byte[] getInvoiceDocument() {
        return invoiceDocument;
    }

    public void setInvoiceDocument(byte[] invoiceDocument) {
        this.invoiceDocument = invoiceDocument;
    }

    public String getInvoiceDocumentContentType() {
        return invoiceDocumentContentType;
    }

    public void setInvoiceDocumentContentType(String invoiceDocumentContentType) {
        this.invoiceDocumentContentType = invoiceDocumentContentType;
    }

    public byte[] getRequisitionDocument() {
        return requisitionDocument;
    }

    public void setRequisitionDocument(byte[] requisitionDocument) {
        this.requisitionDocument = requisitionDocument;
    }

    public String getRequisitionDocumentContentType() {
        return requisitionDocumentContentType;
    }

    public void setRequisitionDocumentContentType(String requisitionDocumentContentType) {
        this.requisitionDocumentContentType = requisitionDocumentContentType;
    }

    public byte[] getApprovalMemoDocument() {
        return approvalMemoDocument;
    }

    public void setApprovalMemoDocument(byte[] approvalMemoDocument) {
        this.approvalMemoDocument = approvalMemoDocument;
    }

    public String getApprovalMemoDocumentContentType() {
        return approvalMemoDocumentContentType;
    }

    public void setApprovalMemoDocumentContentType(String approvalMemoDocumentContentType) {
        this.approvalMemoDocumentContentType = approvalMemoDocumentContentType;
    }

    public byte[] getOtherScannedDocument() {
        return otherScannedDocument;
    }

    public void setOtherScannedDocument(byte[] otherScannedDocument) {
        this.otherScannedDocument = otherScannedDocument;
    }

    public String getOtherScannedDocumentContentType() {
        return otherScannedDocumentContentType;
    }

    public void setOtherScannedDocumentContentType(String otherScannedDocumentContentType) {
        this.otherScannedDocumentContentType = otherScannedDocumentContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ScannedDocumentDTO scannedDocumentDTO = (ScannedDocumentDTO) o;
        if (scannedDocumentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), scannedDocumentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ScannedDocumentDTO{" +
            "id=" + getId() +
            ", documentName='" + getDocumentName() + "'" +
            ", description='" + getDescription() + "'" +
            ", invoiceNumber='" + getInvoiceNumber() + "'" +
            ", transactionId='" + getTransactionId() + "'" +
            ", transactionDate='" + getTransactionDate() + "'" +
            ", invoiceDocument='" + getInvoiceDocument() + "'" +
            ", requisitionDocument='" + getRequisitionDocument() + "'" +
            ", approvalMemoDocument='" + getApprovalMemoDocument() + "'" +
            ", otherScannedDocument='" + getOtherScannedDocument() + "'" +
            "}";
    }
}
