package io.github.prepayments.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A TransactionAccount.
 */
@Entity
@Table(name = "transaction_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "transactionaccount")
public class TransactionAccount implements Serializable {

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
    @Pattern(regexp = "^[0-9]{10,16}$")
    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(name = "account_balance", precision = 21, scale = 2)
    private BigDecimal accountBalance;

    @NotNull
    @Column(name = "opening_date", nullable = false)
    private LocalDate openingDate;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "account_opening_date_balance", precision = 21, scale = 2, nullable = false)
    private BigDecimal accountOpeningDateBalance;

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

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public TransactionAccount accountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public TransactionAccount accountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public TransactionAccount accountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
        return this;
    }

    public LocalDate getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(LocalDate openingDate) {
        this.openingDate = openingDate;
    }

    public TransactionAccount openingDate(LocalDate openingDate) {
        this.openingDate = openingDate;
        return this;
    }

    public BigDecimal getAccountOpeningDateBalance() {
        return accountOpeningDateBalance;
    }

    public void setAccountOpeningDateBalance(BigDecimal accountOpeningDateBalance) {
        this.accountOpeningDateBalance = accountOpeningDateBalance;
    }

    public TransactionAccount accountOpeningDateBalance(BigDecimal accountOpeningDateBalance) {
        this.accountOpeningDateBalance = accountOpeningDateBalance;
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionAccount)) {
            return false;
        }
        return id != null && id.equals(((TransactionAccount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TransactionAccount{" + "id=" + getId() + ", accountName='" + getAccountName() + "'" + ", accountNumber='" + getAccountNumber() + "'" + ", accountBalance=" + getAccountBalance() +
            ", openingDate='" + getOpeningDate() + "'" + ", accountOpeningDateBalance=" + getAccountOpeningDateBalance() + "}";
    }
}
