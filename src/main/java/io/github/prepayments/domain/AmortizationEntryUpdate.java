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
 * A AmortizationEntryUpdate.
 */
@Entity
@Table(name = "amortization_entry_update")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "amortizationentryupdate")
public class AmortizationEntryUpdate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "amortization_entry_id", nullable = false)
    private Long amortizationEntryId;

    @NotNull
    @Column(name = "amortization_date", nullable = false)
    private LocalDate amortizationDate;

    @NotNull
    @Column(name = "amortization_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal amortizationAmount;

    @Column(name = "particulars")
    private String particulars;

    @NotNull
    @Column(name = "prepayment_service_outlet", nullable = false)
    private String prepaymentServiceOutlet;

    @NotNull
    @Column(name = "prepayment_account_number", nullable = false)
    private String prepaymentAccountNumber;

    @NotNull
    @Column(name = "amortization_service_outlet", nullable = false)
    private String amortizationServiceOutlet;

    @NotNull
    @Column(name = "amortization_account_number", nullable = false)
    private String amortizationAccountNumber;

    @NotNull
    @Column(name = "prepayment_entry_id", nullable = false)
    private Long prepaymentEntryId;

    @Column(name = "originating_file_token")
    private String originatingFileToken;

    @Column(name = "amortization_tag")
    private String amortizationTag;

    @Column(name = "orphaned")
    private Boolean orphaned;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmortizationEntryId() {
        return amortizationEntryId;
    }

    public AmortizationEntryUpdate amortizationEntryId(Long amortizationEntryId) {
        this.amortizationEntryId = amortizationEntryId;
        return this;
    }

    public void setAmortizationEntryId(Long amortizationEntryId) {
        this.amortizationEntryId = amortizationEntryId;
    }

    public LocalDate getAmortizationDate() {
        return amortizationDate;
    }

    public AmortizationEntryUpdate amortizationDate(LocalDate amortizationDate) {
        this.amortizationDate = amortizationDate;
        return this;
    }

    public void setAmortizationDate(LocalDate amortizationDate) {
        this.amortizationDate = amortizationDate;
    }

    public BigDecimal getAmortizationAmount() {
        return amortizationAmount;
    }

    public AmortizationEntryUpdate amortizationAmount(BigDecimal amortizationAmount) {
        this.amortizationAmount = amortizationAmount;
        return this;
    }

    public void setAmortizationAmount(BigDecimal amortizationAmount) {
        this.amortizationAmount = amortizationAmount;
    }

    public String getParticulars() {
        return particulars;
    }

    public AmortizationEntryUpdate particulars(String particulars) {
        this.particulars = particulars;
        return this;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public String getPrepaymentServiceOutlet() {
        return prepaymentServiceOutlet;
    }

    public AmortizationEntryUpdate prepaymentServiceOutlet(String prepaymentServiceOutlet) {
        this.prepaymentServiceOutlet = prepaymentServiceOutlet;
        return this;
    }

    public void setPrepaymentServiceOutlet(String prepaymentServiceOutlet) {
        this.prepaymentServiceOutlet = prepaymentServiceOutlet;
    }

    public String getPrepaymentAccountNumber() {
        return prepaymentAccountNumber;
    }

    public AmortizationEntryUpdate prepaymentAccountNumber(String prepaymentAccountNumber) {
        this.prepaymentAccountNumber = prepaymentAccountNumber;
        return this;
    }

    public void setPrepaymentAccountNumber(String prepaymentAccountNumber) {
        this.prepaymentAccountNumber = prepaymentAccountNumber;
    }

    public String getAmortizationServiceOutlet() {
        return amortizationServiceOutlet;
    }

    public AmortizationEntryUpdate amortizationServiceOutlet(String amortizationServiceOutlet) {
        this.amortizationServiceOutlet = amortizationServiceOutlet;
        return this;
    }

    public void setAmortizationServiceOutlet(String amortizationServiceOutlet) {
        this.amortizationServiceOutlet = amortizationServiceOutlet;
    }

    public String getAmortizationAccountNumber() {
        return amortizationAccountNumber;
    }

    public AmortizationEntryUpdate amortizationAccountNumber(String amortizationAccountNumber) {
        this.amortizationAccountNumber = amortizationAccountNumber;
        return this;
    }

    public void setAmortizationAccountNumber(String amortizationAccountNumber) {
        this.amortizationAccountNumber = amortizationAccountNumber;
    }

    public Long getPrepaymentEntryId() {
        return prepaymentEntryId;
    }

    public AmortizationEntryUpdate prepaymentEntryId(Long prepaymentEntryId) {
        this.prepaymentEntryId = prepaymentEntryId;
        return this;
    }

    public void setPrepaymentEntryId(Long prepaymentEntryId) {
        this.prepaymentEntryId = prepaymentEntryId;
    }

    public String getOriginatingFileToken() {
        return originatingFileToken;
    }

    public AmortizationEntryUpdate originatingFileToken(String originatingFileToken) {
        this.originatingFileToken = originatingFileToken;
        return this;
    }

    public void setOriginatingFileToken(String originatingFileToken) {
        this.originatingFileToken = originatingFileToken;
    }

    public String getAmortizationTag() {
        return amortizationTag;
    }

    public AmortizationEntryUpdate amortizationTag(String amortizationTag) {
        this.amortizationTag = amortizationTag;
        return this;
    }

    public void setAmortizationTag(String amortizationTag) {
        this.amortizationTag = amortizationTag;
    }

    public Boolean isOrphaned() {
        return orphaned;
    }

    public AmortizationEntryUpdate orphaned(Boolean orphaned) {
        this.orphaned = orphaned;
        return this;
    }

    public void setOrphaned(Boolean orphaned) {
        this.orphaned = orphaned;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AmortizationEntryUpdate)) {
            return false;
        }
        return id != null && id.equals(((AmortizationEntryUpdate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AmortizationEntryUpdate{" +
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
