package io.github.prepayments.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PrepaymentEntry.
 */
@Entity
@Table(name = "prepayment_entry")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "prepaymententry")
public class PrepaymentEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Pattern(regexp = "^[0-9]{5,16}$")
    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @NotNull
    @Column(name = "account_name", nullable = false)
    private String accountName;

    @NotNull
    @Column(name = "prepayment_id", nullable = false)
    private String prepaymentId;

    @NotNull
    @Column(name = "prepayment_date", nullable = false)
    private LocalDate prepaymentDate;

    @Column(name = "particulars")
    private String particulars;

    @NotNull
    @Pattern(regexp = "^[0-9]{3}$")
    @Column(name = "service_outlet", nullable = false)
    private String serviceOutlet;

    @NotNull
    @Column(name = "prepayment_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal prepaymentAmount;

    @Column(name = "months")
    private Integer months;

    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "scanned_document_id")
    private Long scannedDocumentId;

    @Column(name = "originating_file_token")
    private String OriginatingFileToken;

    @OneToMany(mappedBy = "prepaymentEntry")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AmortizationEntry> amortizationEntries = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public PrepaymentEntry accountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public PrepaymentEntry accountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPrepaymentId() {
        return prepaymentId;
    }

    public PrepaymentEntry prepaymentId(String prepaymentId) {
        this.prepaymentId = prepaymentId;
        return this;
    }

    public void setPrepaymentId(String prepaymentId) {
        this.prepaymentId = prepaymentId;
    }

    public LocalDate getPrepaymentDate() {
        return prepaymentDate;
    }

    public PrepaymentEntry prepaymentDate(LocalDate prepaymentDate) {
        this.prepaymentDate = prepaymentDate;
        return this;
    }

    public void setPrepaymentDate(LocalDate prepaymentDate) {
        this.prepaymentDate = prepaymentDate;
    }

    public String getParticulars() {
        return particulars;
    }

    public PrepaymentEntry particulars(String particulars) {
        this.particulars = particulars;
        return this;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public String getServiceOutlet() {
        return serviceOutlet;
    }

    public PrepaymentEntry serviceOutlet(String serviceOutlet) {
        this.serviceOutlet = serviceOutlet;
        return this;
    }

    public void setServiceOutlet(String serviceOutlet) {
        this.serviceOutlet = serviceOutlet;
    }

    public BigDecimal getPrepaymentAmount() {
        return prepaymentAmount;
    }

    public PrepaymentEntry prepaymentAmount(BigDecimal prepaymentAmount) {
        this.prepaymentAmount = prepaymentAmount;
        return this;
    }

    public void setPrepaymentAmount(BigDecimal prepaymentAmount) {
        this.prepaymentAmount = prepaymentAmount;
    }

    public Integer getMonths() {
        return months;
    }

    public PrepaymentEntry months(Integer months) {
        this.months = months;
        return this;
    }

    public void setMonths(Integer months) {
        this.months = months;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public PrepaymentEntry supplierName(String supplierName) {
        this.supplierName = supplierName;
        return this;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public PrepaymentEntry invoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
        return this;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Long getScannedDocumentId() {
        return scannedDocumentId;
    }

    public PrepaymentEntry scannedDocumentId(Long scannedDocumentId) {
        this.scannedDocumentId = scannedDocumentId;
        return this;
    }

    public void setScannedDocumentId(Long scannedDocumentId) {
        this.scannedDocumentId = scannedDocumentId;
    }

    public String getOriginatingFileToken() {
        return OriginatingFileToken;
    }

    public PrepaymentEntry OriginatingFileToken(String OriginatingFileToken) {
        this.OriginatingFileToken = OriginatingFileToken;
        return this;
    }

    public void setOriginatingFileToken(String OriginatingFileToken) {
        this.OriginatingFileToken = OriginatingFileToken;
    }

    public Set<AmortizationEntry> getAmortizationEntries() {
        return amortizationEntries;
    }

    public PrepaymentEntry amortizationEntries(Set<AmortizationEntry> amortizationEntries) {
        this.amortizationEntries = amortizationEntries;
        return this;
    }

    public PrepaymentEntry addAmortizationEntry(AmortizationEntry amortizationEntry) {
        this.amortizationEntries.add(amortizationEntry);
        amortizationEntry.setPrepaymentEntry(this);
        return this;
    }

    public PrepaymentEntry removeAmortizationEntry(AmortizationEntry amortizationEntry) {
        this.amortizationEntries.remove(amortizationEntry);
        amortizationEntry.setPrepaymentEntry(null);
        return this;
    }

    public void setAmortizationEntries(Set<AmortizationEntry> amortizationEntries) {
        this.amortizationEntries = amortizationEntries;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrepaymentEntry)) {
            return false;
        }
        return id != null && id.equals(((PrepaymentEntry) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PrepaymentEntry{" +
            "id=" + getId() +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", accountName='" + getAccountName() + "'" +
            ", prepaymentId='" + getPrepaymentId() + "'" +
            ", prepaymentDate='" + getPrepaymentDate() + "'" +
            ", particulars='" + getParticulars() + "'" +
            ", serviceOutlet='" + getServiceOutlet() + "'" +
            ", prepaymentAmount=" + getPrepaymentAmount() +
            ", months=" + getMonths() +
            ", supplierName='" + getSupplierName() + "'" +
            ", invoiceNumber='" + getInvoiceNumber() + "'" +
            ", scannedDocumentId=" + getScannedDocumentId() +
            ", OriginatingFileToken='" + getOriginatingFileToken() + "'" +
            "}";
    }
}
