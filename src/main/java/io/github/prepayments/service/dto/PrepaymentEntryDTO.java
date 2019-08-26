package io.github.prepayments.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link io.github.prepayments.domain.PrepaymentEntry} entity.
 */
public class PrepaymentEntryDTO implements Serializable {

    private Long id;

    @NotNull
    @Pattern(regexp = "^[0-9]{5,16}$")
    private String accountNumber;

    @NotNull
    private String accountName;

    @NotNull
    private String prepaymentId;

    @NotNull
    private LocalDate prepaymentDate;

    private String particulars;

    @NotNull
    @Pattern(regexp = "^[0-9]{3}$")
    private String serviceOutlet;

    @NotNull
    private BigDecimal prepaymentAmount;

    private Integer months;

    private String supplierName;

    private String invoiceNumber;

    private Long scannedDocumentId;

    private String OriginatingFileToken;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPrepaymentId() {
        return prepaymentId;
    }

    public void setPrepaymentId(String prepaymentId) {
        this.prepaymentId = prepaymentId;
    }

    public LocalDate getPrepaymentDate() {
        return prepaymentDate;
    }

    public void setPrepaymentDate(LocalDate prepaymentDate) {
        this.prepaymentDate = prepaymentDate;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public String getServiceOutlet() {
        return serviceOutlet;
    }

    public void setServiceOutlet(String serviceOutlet) {
        this.serviceOutlet = serviceOutlet;
    }

    public BigDecimal getPrepaymentAmount() {
        return prepaymentAmount;
    }

    public void setPrepaymentAmount(BigDecimal prepaymentAmount) {
        this.prepaymentAmount = prepaymentAmount;
    }

    public Integer getMonths() {
        return months;
    }

    public void setMonths(Integer months) {
        this.months = months;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Long getScannedDocumentId() {
        return scannedDocumentId;
    }

    public void setScannedDocumentId(Long scannedDocumentId) {
        this.scannedDocumentId = scannedDocumentId;
    }

    public String getOriginatingFileToken() {
        return OriginatingFileToken;
    }

    public void setOriginatingFileToken(String OriginatingFileToken) {
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

        PrepaymentEntryDTO prepaymentEntryDTO = (PrepaymentEntryDTO) o;
        if (prepaymentEntryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prepaymentEntryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PrepaymentEntryDTO{" + "id=" + getId() + ", accountNumber='" + getAccountNumber() + "'" + ", accountName='" + getAccountName() + "'" + ", prepaymentId='" + getPrepaymentId() + "'" +
            ", prepaymentDate='" + getPrepaymentDate() + "'" + ", particulars='" + getParticulars() + "'" + ", serviceOutlet='" + getServiceOutlet() + "'" + ", prepaymentAmount=" +
            getPrepaymentAmount() + ", months=" + getMonths() + ", supplierName='" + getSupplierName() + "'" + ", invoiceNumber='" + getInvoiceNumber() + "'" + ", scannedDocumentId=" +
            getScannedDocumentId() + ", OriginatingFileToken='" + getOriginatingFileToken() + "'" + "}";
    }
}
