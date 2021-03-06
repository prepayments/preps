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
 * Criteria class for the {@link io.github.prepayments.domain.AccountingTransaction} entity. This class is used
 * in {@link io.github.prepayments.web.rest.AccountingTransactionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /accounting-transactions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AccountingTransactionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter description;

    private StringFilter serviceOutletCode;

    private StringFilter accountName;

    private StringFilter accountNumber;

    private LocalDateFilter transactionDate;

    private BigDecimalFilter transactionAmount;

    private BooleanFilter incrementAccount;

    private StringFilter originatingFileToken;

    public AccountingTransactionCriteria(){
    }

    public AccountingTransactionCriteria(AccountingTransactionCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.serviceOutletCode = other.serviceOutletCode == null ? null : other.serviceOutletCode.copy();
        this.accountName = other.accountName == null ? null : other.accountName.copy();
        this.accountNumber = other.accountNumber == null ? null : other.accountNumber.copy();
        this.transactionDate = other.transactionDate == null ? null : other.transactionDate.copy();
        this.transactionAmount = other.transactionAmount == null ? null : other.transactionAmount.copy();
        this.incrementAccount = other.incrementAccount == null ? null : other.incrementAccount.copy();
        this.originatingFileToken = other.originatingFileToken == null ? null : other.originatingFileToken.copy();
    }

    @Override
    public AccountingTransactionCriteria copy() {
        return new AccountingTransactionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getServiceOutletCode() {
        return serviceOutletCode;
    }

    public void setServiceOutletCode(StringFilter serviceOutletCode) {
        this.serviceOutletCode = serviceOutletCode;
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

    public LocalDateFilter getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateFilter transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimalFilter getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimalFilter transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public BooleanFilter getIncrementAccount() {
        return incrementAccount;
    }

    public void setIncrementAccount(BooleanFilter incrementAccount) {
        this.incrementAccount = incrementAccount;
    }

    public StringFilter getOriginatingFileToken() {
        return originatingFileToken;
    }

    public void setOriginatingFileToken(StringFilter originatingFileToken) {
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
        final AccountingTransactionCriteria that = (AccountingTransactionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(description, that.description) &&
            Objects.equals(serviceOutletCode, that.serviceOutletCode) &&
            Objects.equals(accountName, that.accountName) &&
            Objects.equals(accountNumber, that.accountNumber) &&
            Objects.equals(transactionDate, that.transactionDate) &&
            Objects.equals(transactionAmount, that.transactionAmount) &&
            Objects.equals(incrementAccount, that.incrementAccount) &&
            Objects.equals(originatingFileToken, that.originatingFileToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        description,
        serviceOutletCode,
        accountName,
        accountNumber,
        transactionDate,
        transactionAmount,
        incrementAccount,
        originatingFileToken
        );
    }

    @Override
    public String toString() {
        return "AccountingTransactionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (serviceOutletCode != null ? "serviceOutletCode=" + serviceOutletCode + ", " : "") +
                (accountName != null ? "accountName=" + accountName + ", " : "") +
                (accountNumber != null ? "accountNumber=" + accountNumber + ", " : "") +
                (transactionDate != null ? "transactionDate=" + transactionDate + ", " : "") +
                (transactionAmount != null ? "transactionAmount=" + transactionAmount + ", " : "") +
                (incrementAccount != null ? "incrementAccount=" + incrementAccount + ", " : "") +
                (originatingFileToken != null ? "originatingFileToken=" + originatingFileToken + ", " : "") +
            "}";
    }

}
