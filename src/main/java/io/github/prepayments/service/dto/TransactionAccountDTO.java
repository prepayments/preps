package io.github.prepayments.service.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link io.github.prepayments.domain.TransactionAccount} entity.
 */
public class TransactionAccountDTO implements Serializable {

    private Long id;

    @NotNull
    private String accountName;

    @NotNull
    @Pattern(regexp = "^[0-9]{10,16}$")
    private String accountNumber;

    private BigDecimal accountBalance;

    @NotNull
    private LocalDate openingDate;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal accountOpeningDateBalance;


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

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public LocalDate getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(LocalDate openingDate) {
        this.openingDate = openingDate;
    }

    public BigDecimal getAccountOpeningDateBalance() {
        return accountOpeningDateBalance;
    }

    public void setAccountOpeningDateBalance(BigDecimal accountOpeningDateBalance) {
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
        return "TransactionAccountDTO{" + "id=" + getId() + ", accountName='" + getAccountName() + "'" + ", accountNumber='" + getAccountNumber() + "'" + ", accountBalance=" + getAccountBalance() +
            ", openingDate='" + getOpeningDate() + "'" + ", accountOpeningDateBalance=" + getAccountOpeningDateBalance() + "}";
    }
}
