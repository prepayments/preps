package io.github.prepayments.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link io.github.prepayments.domain.AmortizationEntry} entity.
 */
public class AmortizationEntryDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate amortizationDate;

    @NotNull
    private BigDecimal amortizationAmount;

    private String particulars;

    private Boolean posted;

    @NotNull
    @Pattern(regexp = "^[0-9]{3}$")
    private String serviceOutlet;

    @NotNull
    @Pattern(regexp = "^[0-9]{10,16}$")
    private String accountNumber;

    @NotNull
    private String accountName;


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

    public Boolean isPosted() {
        return posted;
    }

    public void setPosted(Boolean posted) {
        this.posted = posted;
    }

    public String getServiceOutlet() {
        return serviceOutlet;
    }

    public void setServiceOutlet(String serviceOutlet) {
        this.serviceOutlet = serviceOutlet;
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

        AmortizationEntryDTO amortizationEntryDTO = (AmortizationEntryDTO) o;
        if (amortizationEntryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), amortizationEntryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AmortizationEntryDTO{" +
            "id=" + getId() +
            ", amortizationDate='" + getAmortizationDate() + "'" +
            ", amortizationAmount=" + getAmortizationAmount() +
            ", particulars='" + getParticulars() + "'" +
            ", posted='" + isPosted() + "'" +
            ", serviceOutlet='" + getServiceOutlet() + "'" +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", accountName='" + getAccountName() + "'" +
            ", prepaymentEntry=" + getPrepaymentEntryId() +
            "}";
    }
}
