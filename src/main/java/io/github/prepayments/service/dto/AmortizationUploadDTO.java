package io.github.prepayments.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link io.github.prepayments.domain.AmortizationUpload} entity.
 */
public class AmortizationUploadDTO implements Serializable {

    private Long id;

    @NotNull
    private String accountName;

    @NotNull
    private String particulars;

    @NotNull
    private String amortizationServiceOutletCode;

    @NotNull
    private String prepaymentServiceOutletCode;

    @NotNull
    private String prepaymentAccountNumber;

    @NotNull
    private String expenseAccountNumber;

    @NotNull
    private String prepaymentTransactionId;

    @NotNull
    private LocalDate prepaymentTransactionDate;

    private BigDecimal prepaymentTransactionAmount;

    @NotNull
    private BigDecimal amortizationAmount;

    @NotNull
    @Min(value = 1)
    private Integer numberOfAmortizations;

    @NotNull
    private LocalDate firstAmortizationDate;

    private Boolean uploadSuccessful;

    private Boolean uploadOrphaned;

    private String originatingFileToken;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public String getAmortizationServiceOutletCode() {
        return amortizationServiceOutletCode;
    }

    public void setAmortizationServiceOutletCode(String amortizationServiceOutletCode) {
        this.amortizationServiceOutletCode = amortizationServiceOutletCode;
    }

    public String getPrepaymentServiceOutletCode() {
        return prepaymentServiceOutletCode;
    }

    public void setPrepaymentServiceOutletCode(String prepaymentServiceOutletCode) {
        this.prepaymentServiceOutletCode = prepaymentServiceOutletCode;
    }

    public String getPrepaymentAccountNumber() {
        return prepaymentAccountNumber;
    }

    public void setPrepaymentAccountNumber(String prepaymentAccountNumber) {
        this.prepaymentAccountNumber = prepaymentAccountNumber;
    }

    public String getExpenseAccountNumber() {
        return expenseAccountNumber;
    }

    public void setExpenseAccountNumber(String expenseAccountNumber) {
        this.expenseAccountNumber = expenseAccountNumber;
    }

    public String getPrepaymentTransactionId() {
        return prepaymentTransactionId;
    }

    public void setPrepaymentTransactionId(String prepaymentTransactionId) {
        this.prepaymentTransactionId = prepaymentTransactionId;
    }

    public LocalDate getPrepaymentTransactionDate() {
        return prepaymentTransactionDate;
    }

    public void setPrepaymentTransactionDate(LocalDate prepaymentTransactionDate) {
        this.prepaymentTransactionDate = prepaymentTransactionDate;
    }

    public BigDecimal getPrepaymentTransactionAmount() {
        return prepaymentTransactionAmount;
    }

    public void setPrepaymentTransactionAmount(BigDecimal prepaymentTransactionAmount) {
        this.prepaymentTransactionAmount = prepaymentTransactionAmount;
    }

    public BigDecimal getAmortizationAmount() {
        return amortizationAmount;
    }

    public void setAmortizationAmount(BigDecimal amortizationAmount) {
        this.amortizationAmount = amortizationAmount;
    }

    public Integer getNumberOfAmortizations() {
        return numberOfAmortizations;
    }

    public void setNumberOfAmortizations(Integer numberOfAmortizations) {
        this.numberOfAmortizations = numberOfAmortizations;
    }

    public LocalDate getFirstAmortizationDate() {
        return firstAmortizationDate;
    }

    public void setFirstAmortizationDate(LocalDate firstAmortizationDate) {
        this.firstAmortizationDate = firstAmortizationDate;
    }

    public Boolean isUploadSuccessful() {
        return uploadSuccessful;
    }

    public void setUploadSuccessful(Boolean uploadSuccessful) {
        this.uploadSuccessful = uploadSuccessful;
    }

    public Boolean isUploadOrphaned() {
        return uploadOrphaned;
    }

    public void setUploadOrphaned(Boolean uploadOrphaned) {
        this.uploadOrphaned = uploadOrphaned;
    }

    public String getOriginatingFileToken() {
        return originatingFileToken;
    }

    public void setOriginatingFileToken(String originatingFileToken) {
        this.originatingFileToken = originatingFileToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AmortizationUploadDTO amortizationUploadDTO = (AmortizationUploadDTO) o;
        if (amortizationUploadDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), amortizationUploadDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AmortizationUploadDTO{" +
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
