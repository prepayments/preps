package io.github.prepayments.service.dto;

import io.github.prepayments.app.token.IsTokenized;
import io.github.prepayments.domain.enumeration.AccountTypes;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link io.github.prepayments.domain.TransactionAccount} entity.
 */
public class TransactionAccountDTO implements Serializable,IsTokenized<String> {

    private Long id;

    @NotNull
    private String accountName;

    @NotNull
    private String accountNumber;

    private AccountTypes accountType;

    private LocalDate openingDate;

    private String originatingFileToken;

    public String originatingFileToken() {
        return this.originatingFileToken;
    }

    public IsTokenized<String> originatingFileToken(final String token) {
        this.originatingFileToken = token;
        return this;
    }


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

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountTypes getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountTypes accountType) {
        this.accountType = accountType;
    }

    public LocalDate getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(LocalDate openingDate) {
        this.openingDate = openingDate;
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

        TransactionAccountDTO transactionAccountDTO = (TransactionAccountDTO) o;
        if (transactionAccountDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transactionAccountDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TransactionAccountDTO{" + "id=" + getId() + ", accountName='" + getAccountName() + "'" + ", accountNumber='" + getAccountNumber() + "'" + ", accountType='" + getAccountType() + "'" +
            ", openingDate='" + getOpeningDate() + "'" + ", originatingFileToken='" + getOriginatingFileToken() + "'" + "}";
    }
}
