package io.github.prepayments.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @Column(name = "posted")
    private Boolean posted;

    @NotNull
    @Pattern(regexp = "^[0-9]{3}$")
    @Column(name = "service_outlet", nullable = false)
    private String serviceOutlet;

    @NotNull
    @Pattern(regexp = "^[0-9]{10,16}$")
    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @NotNull
    @Column(name = "account_name", nullable = false)
    private String accountName;

    @ManyToOne(optional = false)
    @NotNull
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

    public AmortizationEntry amortizationDate(LocalDate amortizationDate) {
        this.amortizationDate = amortizationDate;
        return this;
    }

    public void setAmortizationDate(LocalDate amortizationDate) {
        this.amortizationDate = amortizationDate;
    }

    public BigDecimal getAmortizationAmount() {
        return amortizationAmount;
    }

    public AmortizationEntry amortizationAmount(BigDecimal amortizationAmount) {
        this.amortizationAmount = amortizationAmount;
        return this;
    }

    public void setAmortizationAmount(BigDecimal amortizationAmount) {
        this.amortizationAmount = amortizationAmount;
    }

    public String getParticulars() {
        return particulars;
    }

    public AmortizationEntry particulars(String particulars) {
        this.particulars = particulars;
        return this;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public Boolean isPosted() {
        return posted;
    }

    public AmortizationEntry posted(Boolean posted) {
        this.posted = posted;
        return this;
    }

    public void setPosted(Boolean posted) {
        this.posted = posted;
    }

    public String getServiceOutlet() {
        return serviceOutlet;
    }

    public AmortizationEntry serviceOutlet(String serviceOutlet) {
        this.serviceOutlet = serviceOutlet;
        return this;
    }

    public void setServiceOutlet(String serviceOutlet) {
        this.serviceOutlet = serviceOutlet;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public AmortizationEntry accountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public AmortizationEntry accountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public PrepaymentEntry getPrepaymentEntry() {
        return prepaymentEntry;
    }

    public AmortizationEntry prepaymentEntry(PrepaymentEntry prepaymentEntry) {
        this.prepaymentEntry = prepaymentEntry;
        return this;
    }

    public void setPrepaymentEntry(PrepaymentEntry prepaymentEntry) {
        this.prepaymentEntry = prepaymentEntry;
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
        return "AmortizationEntry{" +
            "id=" + getId() +
            ", amortizationDate='" + getAmortizationDate() + "'" +
            ", amortizationAmount=" + getAmortizationAmount() +
            ", particulars='" + getParticulars() + "'" +
            ", posted='" + isPosted() + "'" +
            ", serviceOutlet='" + getServiceOutlet() + "'" +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", accountName='" + getAccountName() + "'" +
            "}";
    }
}
