package io.github.prepayments.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link io.github.prepayments.domain.AccountingTransaction} entity.
 */
public class AccountingTransactionDTO implements Serializable {

    private Long id;

    private String description;

    @NotNull
    private String serviceOutletCode;

    @NotNull
    private String accountName;

    @NotNull
    private String accountNumber;

    @NotNull
    private LocalDate transactionDate;

    @NotNull
    private BigDecimal transactionAmount;

    @NotNull
    private Boolean incrementAccount;

    private String originatingFileToken;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServiceOutletCode() {
        return serviceOutletCode;
    }

    public void setServiceOutletCode(String serviceOutletCode) {
        this.serviceOutletCode = serviceOutletCode;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Boolean isIncrementAccount() {
        return incrementAccount;
    }

    public void setIncrementAccount(Boolean incrementAccount) {
        this.incrementAccount = incrementAccount;
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

        AccountingTransactionDTO accountingTransactionDTO = (AccountingTransactionDTO) o;
        if (accountingTransactionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), accountingTransactionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AccountingTransactionDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", serviceOutletCode='" + getServiceOutletCode() + "'" +
            ", accountName='" + getAccountName() + "'" +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", transactionDate='" + getTransactionDate() + "'" +
            ", transactionAmount=" + getTransactionAmount() +
            ", incrementAccount='" + isIncrementAccount() + "'" +
            ", originatingFileToken='" + getOriginatingFileToken() + "'" +
            "}";
    }
}
