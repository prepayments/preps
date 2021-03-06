package io.github.prepayments.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A AmortizationEntry.
 */
@Entity
@Table(name = "amortization_entry")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "amortizationentry")
public class AmortizationEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

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

    @Column(name = "originating_file_token")
    private String originatingFileToken;

    @Column(name = "amortization_tag")
    private String amortizationTag;

    @Column(name = "orphaned")
    private Boolean orphaned;

    @ManyToOne
    @JsonIgnoreProperties("amortizationEntries")
    private PrepaymentEntry prepaymentEntry;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
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

    public AmortizationEntry amortizationDate(LocalDate amortizationDate) {
        this.amortizationDate = amortizationDate;
        return this;
    }

    public BigDecimal getAmortizationAmount() {
        return amortizationAmount;
    }

    public void setAmortizationAmount(BigDecimal amortizationAmount) {
        this.amortizationAmount = amortizationAmount;
    }

    public AmortizationEntry amortizationAmount(BigDecimal amortizationAmount) {
        this.amortizationAmount = amortizationAmount;
        return this;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public AmortizationEntry particulars(String particulars) {
        this.particulars = particulars;
        return this;
    }

    public String getPrepaymentServiceOutlet() {
        return prepaymentServiceOutlet;
    }

    public void setPrepaymentServiceOutlet(String prepaymentServiceOutlet) {
        this.prepaymentServiceOutlet = prepaymentServiceOutlet;
    }

    public AmortizationEntry prepaymentServiceOutlet(String prepaymentServiceOutlet) {
        this.prepaymentServiceOutlet = prepaymentServiceOutlet;
        return this;
    }

    public String getPrepaymentAccountNumber() {
        return prepaymentAccountNumber;
    }

    public void setPrepaymentAccountNumber(String prepaymentAccountNumber) {
        this.prepaymentAccountNumber = prepaymentAccountNumber;
    }

    public AmortizationEntry prepaymentAccountNumber(String prepaymentAccountNumber) {
        this.prepaymentAccountNumber = prepaymentAccountNumber;
        return this;
    }

    public String getAmortizationServiceOutlet() {
        return amortizationServiceOutlet;
    }

    public void setAmortizationServiceOutlet(String amortizationServiceOutlet) {
        this.amortizationServiceOutlet = amortizationServiceOutlet;
    }

    public AmortizationEntry amortizationServiceOutlet(String amortizationServiceOutlet) {
        this.amortizationServiceOutlet = amortizationServiceOutlet;
        return this;
    }

    public String getAmortizationAccountNumber() {
        return amortizationAccountNumber;
    }

    public void setAmortizationAccountNumber(String amortizationAccountNumber) {
        this.amortizationAccountNumber = amortizationAccountNumber;
    }

    public AmortizationEntry amortizationAccountNumber(String amortizationAccountNumber) {
        this.amortizationAccountNumber = amortizationAccountNumber;
        return this;
    }

    public String getOriginatingFileToken() {
        return originatingFileToken;
    }

    public void setOriginatingFileToken(String originatingFileToken) {
        this.originatingFileToken = originatingFileToken;
    }

    public AmortizationEntry originatingFileToken(String originatingFileToken) {
        this.originatingFileToken = originatingFileToken;
        return this;
    }

    public String getAmortizationTag() {
        return amortizationTag;
    }

    public void setAmortizationTag(String amortizationTag) {
        this.amortizationTag = amortizationTag;
    }

    public AmortizationEntry amortizationTag(String amortizationTag) {
        this.amortizationTag = amortizationTag;
        return this;
    }

    public Boolean isOrphaned() {
        return orphaned;
    }

    public AmortizationEntry orphaned(Boolean orphaned) {
        this.orphaned = orphaned;
        return this;
    }

    public void setOrphaned(Boolean orphaned) {
        this.orphaned = orphaned;
    }

    public PrepaymentEntry getPrepaymentEntry() {
        return prepaymentEntry;
    }

    public void setPrepaymentEntry(PrepaymentEntry prepaymentEntry) {
        this.prepaymentEntry = prepaymentEntry;
    }

    public AmortizationEntry prepaymentEntry(PrepaymentEntry prepaymentEntry) {
        this.prepaymentEntry = prepaymentEntry;
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AmortizationEntry)) {
            return false;
        }
        return id != null && id.equals(((AmortizationEntry) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AmortizationEntry{" + "id=" + getId() + ", amortizationDate='" + getAmortizationDate() + "'" + ", amortizationAmount=" + getAmortizationAmount() + ", particulars='" +
            getParticulars() + "'" + ", prepaymentServiceOutlet='" + getPrepaymentServiceOutlet() + "'" + ", prepaymentAccountNumber='" + getPrepaymentAccountNumber() + "'" +
            ", amortizationServiceOutlet='" + getAmortizationServiceOutlet() + "'" + ", amortizationAccountNumber='" + getAmortizationAccountNumber() + "'" + ", originatingFileToken='" +
            getOriginatingFileToken() + "'" + ", amortizationTag='" + getAmortizationTag() + "'" + ", orphaned='" + isOrphaned() + "'" + "}";
    }
}
