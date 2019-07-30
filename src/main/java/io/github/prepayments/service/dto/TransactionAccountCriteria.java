package io.github.prepayments.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link io.github.prepayments.domain.TransactionAccount} entity. This class is used
 * in {@link io.github.prepayments.web.rest.TransactionAccountResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /transaction-accounts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TransactionAccountCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter accountName;

    private StringFilter accountNumber;

    private BigDecimalFilter accountBalance;

    private LocalDateFilter openingDate;

    private BigDecimalFilter accountOpeningDateBalance;

    public TransactionAccountCriteria(){
    }

    public TransactionAccountCriteria(TransactionAccountCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.accountName = other.accountName == null ? null : other.accountName.copy();
        this.accountNumber = other.accountNumber == null ? null : other.accountNumber.copy();
        this.accountBalance = other.accountBalance == null ? null : other.accountBalance.copy();
        this.openingDate = other.openingDate == null ? null : other.openingDate.copy();
        this.accountOpeningDateBalance = other.accountOpeningDateBalance == null ? null : other.accountOpeningDateBalance.copy();
    }

    @Override
    public TransactionAccountCriteria copy() {
        return new TransactionAccountCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAccountName() {
        return accountName;
    }

    public void setAccountName(StringFilter accountName) {
        this.accountName = accountName;
    }

    public StringFilter getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(StringFilter accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimalFilter getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimalFilter accountBalance) {
        this.accountBalance = accountBalance;
    }

    public LocalDateFilter getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(LocalDateFilter openingDate) {
        this.openingDate = openingDate;
    }

    public BigDecimalFilter getAccountOpeningDateBalance() {
        return accountOpeningDateBalance;
    }

    public void setAccountOpeningDateBalance(BigDecimalFilter accountOpeningDateBalance) {
        this.accountOpeningDateBalance = accountOpeningDateBalance;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TransactionAccountCriteria that = (TransactionAccountCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(accountName, that.accountName) &&
            Objects.equals(accountNumber, that.accountNumber) &&
            Objects.equals(accountBalance, that.accountBalance) &&
            Objects.equals(openingDate, that.openingDate) &&
            Objects.equals(accountOpeningDateBalance, that.accountOpeningDateBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        accountName,
        accountNumber,
        accountBalance,
        openingDate,
        accountOpeningDateBalance
        );
    }

    @Override
    public String toString() {
        return "TransactionAccountCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (accountName != null ? "accountName=" + accountName + ", " : "") +
                (accountNumber != null ? "accountNumber=" + accountNumber + ", " : "") +
                (accountBalance != null ? "accountBalance=" + accountBalance + ", " : "") +
                (openingDate != null ? "openingDate=" + openingDate + ", " : "") +
                (accountOpeningDateBalance != null ? "accountOpeningDateBalance=" + accountOpeningDateBalance + ", " : "") +
            "}";
    }

}
