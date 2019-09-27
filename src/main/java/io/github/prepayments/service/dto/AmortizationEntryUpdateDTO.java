package io.github.prepayments.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link io.github.prepayments.domain.AmortizationEntryUpdate} entity.
 */
public class AmortizationEntryUpdateDTO implements Serializable {

    private Long id;

    @NotNull
    private Long amortizationEntryId;

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

    @NotNull
    private Long prepaymentEntryId;

    private String originatingFileToken;

    private String amortizationTag;

    private Boolean orphaned;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmortizationEntryId() {
        return amortizationEntryId;
    }

    public void setAmortizationEntryId(Long amortizationEntryId) {
        this.amortizationEntryId = amortizationEntryId;
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

    public Long getPrepaymentEntryId() {
        return prepaymentEntryId;
    }

    public void setPrepaymentEntryId(Long prepaymentEntryId) {
        this.prepaymentEntryId = prepaymentEntryId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AmortizationEntryUpdateDTO amortizationEntryUpdateDTO = (AmortizationEntryUpdateDTO) o;
        if (amortizationEntryUpdateDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), amortizationEntryUpdateDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AmortizationEntryUpdateDTO{" +
            "id=" + getId() +
            ", amortizationEntryId=" + getAmortizationEntryId() +
            ", amortizationDate='" + getAmortizationDate() + "'" +
            ", amortizationAmount=" + getAmortizationAmount() +
            ", particulars='" + getParticulars() + "'" +
            ", prepaymentServiceOutlet='" + getPrepaymentServiceOutlet() + "'" +
            ", prepaymentAccountNumber='" + getPrepaymentAccountNumber() + "'" +
            ", amortizationServiceOutlet='" + getAmortizationServiceOutlet() + "'" +
            ", amortizationAccountNumber='" + getAmortizationAccountNumber() + "'" +
            ", prepaymentEntryId=" + getPrepaymentEntryId() +
            ", originatingFileToken='" + getOriginatingFileToken() + "'" +
            ", amortizationTag='" + getAmortizationTag() + "'" +
            ", orphaned='" + isOrphaned() + "'" +
            "}";
    }
}
