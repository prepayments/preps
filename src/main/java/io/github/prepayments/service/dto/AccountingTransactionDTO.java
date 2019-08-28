package io.github.prepayments.service.dto;

import io.github.prepayments.app.token.IsTokenized;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link io.github.prepayments.domain.AccountingTransaction} entity.
 */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AccountingTransactionDTO implements Serializable, IsTokenized<String> {

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

    public String originatingFileToken() {
        return this.originatingFileToken;
    }

    public IsTokenized<String> originatingFileToken(final String token) {
        this.originatingFileToken = token;
        return this;
    }
}
