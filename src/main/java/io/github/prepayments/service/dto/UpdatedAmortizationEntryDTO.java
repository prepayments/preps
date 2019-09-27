package io.github.prepayments.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link io.github.prepayments.domain.UpdatedAmortizationEntry} entity.
 */
public class UpdatedAmortizationEntryDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate amortizationDate;

    @NotNull
    private BigDecimal amortizationAmount;

    private String particulars;

    @NotNull
    private String prepaymentServiceOutlet;

    @NotNull
    private String prepaymentAccountNumber;

    @NotNull
    private String amortizationServiceOutlet;

    @NotNull
    private String amortizationAccountNumber;

    private String originatingFileToken;

    private String amortizationTag;

    private Boolean orphaned;

    @NotNull
    private LocalDate dateOfUpdate;

    private String reasonForUpdate;


    private Long prepaymentEntryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getAmortizationDate() {
        return amortizationDate;
    }

    public void setAmortizationDate(LocalDate amortizationDate) {
        this.amortizationDate = amortizationDate;
    }

    public BigDecimal getAmortizationAmount() {
        return amortizationAmount;
    }

    public void setAmortizationAmount(BigDecimal amortizationAmount) {
        this.amortizationAmount = amortizationAmount;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public String getPrepaymentServiceOutlet() {
        return prepaymentServiceOutlet;
    }

    public void setPrepaymentServiceOutlet(String prepaymentServiceOutlet) {
        this.prepaymentServiceOutlet = prepaymentServiceOutlet;
    }

    public String getPrepaymentAccountNumber() {
        return prepaymentAccountNumber;
    }

    public void setPrepaymentAccountNumber(String prepaymentAccountNumber) {
        this.prepaymentAccountNumber = prepaymentAccountNumber;
    }

    public String getAmortizationServiceOutlet() {
        return amortizationServiceOutlet;
    }

    public void setAmortizationServiceOutlet(String amortizationServiceOutlet) {
        this.amortizationServiceOutlet = amortizationServiceOutlet;
    }

    public String getAmortizationAccountNumber() {
        return amortizationAccountNumber;
    }

    public void setAmortizationAccountNumber(String amortizationAccountNumber) {
        this.amortizationAccountNumber = amortizationAccountNumber;
    }

    public String getOriginatingFileToken() {
        return originatingFileToken;
    }

    public void setOriginatingFileToken(String originatingFileToken) {
        this.originatingFileToken = originatingFileToken;
    }

    public String getAmortizationTag() {
        return amortizationTag;
    }

    public void setAmortizationTag(String amortizationTag) {
        this.amortizationTag = amortizationTag;
    }

    public Boolean isOrphaned() {
        return orphaned;
    }

    public void setOrphaned(Boolean orphaned) {
        this.orphaned = orphaned;
    }

    public LocalDate getDateOfUpdate() {
        return dateOfUpdate;
    }

    public void setDateOfUpdate(LocalDate dateOfUpdate) {
        this.dateOfUpdate = dateOfUpdate;
    }

    public String getReasonForUpdate() {
        return reasonForUpdate;
    }

    public void setReasonForUpdate(String reasonForUpdate) {
        this.reasonForUpdate = reasonForUpdate;
    }

    public Long getPrepaymentEntryId() {
        return prepaymentEntryId;
    }

    public void setPrepaymentEntryId(Long prepaymentEntryId) {
        this.prepaymentEntryId = prepaymentEntryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UpdatedAmortizationEntryDTO updatedAmortizationEntryDTO = (UpdatedAmortizationEntryDTO) o;
        if (updatedAmortizationEntryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), updatedAmortizationEntryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UpdatedAmortizationEntryDTO{" +
            "id=" + getId() +
            ", amortizationDate='" + getAmortizationDate() + "'" +
            ", amortizationAmount=" + getAmortizationAmount() +
            ", particulars='" + getParticulars() + "'" +
            ", prepaymentServiceOutlet='" + getPrepaymentServiceOutlet() + "'" +
            ", prepaymentAccountNumber='" + getPrepaymentAccountNumber() + "'" +
            ", amortizationServiceOutlet='" + getAmortizationServiceOutlet() + "'" +
            ", amortizationAccountNumber='" + getAmortizationAccountNumber() + "'" +
            ", originatingFileToken='" + getOriginatingFileToken() + "'" +
            ", amortizationTag='" + getAmortizationTag() + "'" +
            ", orphaned='" + isOrphaned() + "'" +
            ", dateOfUpdate='" + getDateOfUpdate() + "'" +
            ", reasonForUpdate='" + getReasonForUpdate() + "'" +
            ", prepaymentEntry=" + getPrepaymentEntryId() +
            "}";
    }
}
