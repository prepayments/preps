package io.github.prepayments.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A AmortizationUpload.
 */
@Entity
@Table(name = "amortization_upload")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "amortizationupload")
public class AmortizationUpload implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "account_name", nullable = false)
    private String accountName;

    @NotNull
    @Column(name = "particulars", nullable = false)
    private String particulars;

    @NotNull
    @Column(name = "amortization_service_outlet_code", nullable = false)
    private String amortizationServiceOutletCode;

    @NotNull
    @Column(name = "prepayment_service_outlet_code", nullable = false)
    private String prepaymentServiceOutletCode;

    @NotNull
    @Column(name = "prepayment_account_number", nullable = false)
    private String prepaymentAccountNumber;

    @NotNull
    @Column(name = "expense_account_number", nullable = false)
    private String expenseAccountNumber;

    @NotNull
    @Column(name = "prepayment_transaction_id", nullable = false)
    private String prepaymentTransactionId;

    @NotNull
    @Column(name = "prepayment_transaction_date", nullable = false)
    private LocalDate prepaymentTransactionDate;

    @Column(name = "prepayment_transaction_amount", precision = 21, scale = 2)
    private BigDecimal prepaymentTransactionAmount;

    @NotNull
    @Column(name = "amortization_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal amortizationAmount;

    @NotNull
    @Min(value = 1)
    @Column(name = "number_of_amortizations", nullable = false)
    private Integer numberOfAmortizations;

    @NotNull
    @Column(name = "first_amortization_date", nullable = false)
    private LocalDate firstAmortizationDate;

    @Column(name = "upload_successful")
    private Boolean uploadSuccessful;

    @Column(name = "upload_orphaned")
    private Boolean uploadOrphaned;

    @Column(name = "originating_file_token")
    private String originatingFileToken;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public AmortizationUpload accountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getParticulars() {
        return particulars;
    }

    public AmortizationUpload particulars(String particulars) {
        this.particulars = particulars;
        return this;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public String getAmortizationServiceOutletCode() {
        return amortizationServiceOutletCode;
    }

    public AmortizationUpload amortizationServiceOutletCode(String amortizationServiceOutletCode) {
        this.amortizationServiceOutletCode = amortizationServiceOutletCode;
        return this;
    }

    public void setAmortizationServiceOutletCode(String amortizationServiceOutletCode) {
        this.amortizationServiceOutletCode = amortizationServiceOutletCode;
    }

    public String getPrepaymentServiceOutletCode() {
        return prepaymentServiceOutletCode;
    }

    public AmortizationUpload prepaymentServiceOutletCode(String prepaymentServiceOutletCode) {
        this.prepaymentServiceOutletCode = prepaymentServiceOutletCode;
        return this;
    }

    public void setPrepaymentServiceOutletCode(String prepaymentServiceOutletCode) {
        this.prepaymentServiceOutletCode = prepaymentServiceOutletCode;
    }

    public String getPrepaymentAccountNumber() {
        return prepaymentAccountNumber;
    }

    public AmortizationUpload prepaymentAccountNumber(String prepaymentAccountNumber) {
        this.prepaymentAccountNumber = prepaymentAccountNumber;
        return this;
    }

    public void setPrepaymentAccountNumber(String prepaymentAccountNumber) {
        this.prepaymentAccountNumber = prepaymentAccountNumber;
    }

    public String getExpenseAccountNumber() {
        return expenseAccountNumber;
    }

    public AmortizationUpload expenseAccountNumber(String expenseAccountNumber) {
        this.expenseAccountNumber = expenseAccountNumber;
        return this;
    }

    public void setExpenseAccountNumber(String expenseAccountNumber) {
        this.expenseAccountNumber = expenseAccountNumber;
    }

    public String getPrepaymentTransactionId() {
        return prepaymentTransactionId;
    }

    public AmortizationUpload prepaymentTransactionId(String prepaymentTransactionId) {
        this.prepaymentTransactionId = prepaymentTransactionId;
        return this;
    }

    public void setPrepaymentTransactionId(String prepaymentTransactionId) {
        this.prepaymentTransactionId = prepaymentTransactionId;
    }

    public LocalDate getPrepaymentTransactionDate() {
        return prepaymentTransactionDate;
    }

    public AmortizationUpload prepaymentTransactionDate(LocalDate prepaymentTransactionDate) {
        this.prepaymentTransactionDate = prepaymentTransactionDate;
        return this;
    }

    public void setPrepaymentTransactionDate(LocalDate prepaymentTransactionDate) {
        this.prepaymentTransactionDate = prepaymentTransactionDate;
    }

    public BigDecimal getPrepaymentTransactionAmount() {
        return prepaymentTransactionAmount;
    }

    public AmortizationUpload prepaymentTransactionAmount(BigDecimal prepaymentTransactionAmount) {
        this.prepaymentTransactionAmount = prepaymentTransactionAmount;
        return this;
    }

    public void setPrepaymentTransactionAmount(BigDecimal prepaymentTransactionAmount) {
        this.prepaymentTransactionAmount = prepaymentTransactionAmount;
    }

    public BigDecimal getAmortizationAmount() {
        return amortizationAmount;
    }

    public AmortizationUpload amortizationAmount(BigDecimal amortizationAmount) {
        this.amortizationAmount = amortizationAmount;
        return this;
    }

    public void setAmortizationAmount(BigDecimal amortizationAmount) {
        this.amortizationAmount = amortizationAmount;
    }

    public Integer getNumberOfAmortizations() {
        return numberOfAmortizations;
    }

    public AmortizationUpload numberOfAmortizations(Integer numberOfAmortizations) {
        this.numberOfAmortizations = numberOfAmortizations;
        return this;
    }

    public void setNumberOfAmortizations(Integer numberOfAmortizations) {
        this.numberOfAmortizations = numberOfAmortizations;
    }

    public LocalDate getFirstAmortizationDate() {
        return firstAmortizationDate;
    }

    public AmortizationUpload firstAmortizationDate(LocalDate firstAmortizationDate) {
        this.firstAmortizationDate = firstAmortizationDate;
        return this;
    }

    public void setFirstAmortizationDate(LocalDate firstAmortizationDate) {
        this.firstAmortizationDate = firstAmortizationDate;
    }

    public Boolean isUploadSuccessful() {
        return uploadSuccessful;
    }

    public AmortizationUpload uploadSuccessful(Boolean uploadSuccessful) {
        this.uploadSuccessful = uploadSuccessful;
        return this;
    }

    public void setUploadSuccessful(Boolean uploadSuccessful) {
        this.uploadSuccessful = uploadSuccessful;
    }

    public Boolean isUploadOrphaned() {
        return uploadOrphaned;
    }

    public AmortizationUpload uploadOrphaned(Boolean uploadOrphaned) {
        this.uploadOrphaned = uploadOrphaned;
        return this;
    }

    public void setUploadOrphaned(Boolean uploadOrphaned) {
        this.uploadOrphaned = uploadOrphaned;
    }

    public String getOriginatingFileToken() {
        return originatingFileToken;
    }

    public AmortizationUpload originatingFileToken(String originatingFileToken) {
        this.originatingFileToken = originatingFileToken;
        return this;
    }

    public void setOriginatingFileToken(String originatingFileToken) {
        this.originatingFileToken = originatingFileToken;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AmortizationUpload)) {
            return false;
        }
        return id != null && id.equals(((AmortizationUpload) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AmortizationUpload{" +
            "id=" + getId() +
            ", accountName='" + getAccountName() + "'" +
            ", particulars='" + getParticulars() + "'" +
            ", amortizationServiceOutletCode='" + getAmortizationServiceOutletCode() + "'" +
            ", prepaymentServiceOutletCode='" + getPrepaymentServiceOutletCode() + "'" +
            ", prepaymentAccountNumber='" + getPrepaymentAccountNumber() + "'" +
            ", expenseAccountNumber='" + getExpenseAccountNumber() + "'" +
            ", prepaymentTransactionId='" + getPrepaymentTransactionId() + "'" +
            ", prepaymentTransactionDate='" + getPrepaymentTransactionDate() + "'" +
            ", prepaymentTransactionAmount=" + getPrepaymentTransactionAmount() +
            ", amortizationAmount=" + getAmortizationAmount() +
            ", numberOfAmortizations=" + getNumberOfAmortizations() +
            ", firstAmortizationDate='" + getFirstAmortizationDate() + "'" +
            ", uploadSuccessful='" + isUploadSuccessful() + "'" +
            ", uploadOrphaned='" + isUploadOrphaned() + "'" +
            ", originatingFileToken='" + getOriginatingFileToken() + "'" +
            "}";
    }
}
