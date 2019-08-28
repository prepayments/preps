package io.github.prepayments.service.dto;

import io.github.prepayments.app.token.IsTokenized;
import io.github.prepayments.app.token.TagCapable;
import io.github.prepayments.app.token.TagCapableAmortizationModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A DTO for the {@link io.github.prepayments.domain.AmortizationUpload} entity.
 */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AmortizationUploadDTO implements Serializable, TagCapable<String>, TagCapableAmortizationModel,IsTokenized<String> {

    private Long id;

    @NotNull
    private String accountName;

    @NotNull
    private String particulars;

    @NotNull
    private String amortizationServiceOutletCode;

    @NotNull
    private String prepaymentServiceOutletCode;

    @NotNull
    private String prepaymentAccountNumber;

    @NotNull
    private String expenseAccountNumber;

    @NotNull
    private String prepaymentTransactionId;

    @NotNull
    private LocalDate prepaymentTransactionDate;

    private BigDecimal prepaymentTransactionAmount;

    @NotNull
    private BigDecimal amortizationAmount;

    @NotNull
    @Min(value = 1)
    private Integer numberOfAmortizations;

    @NotNull
    private LocalDate firstAmortizationDate;

    @Min(value = 1)
    @Max(value = 28)
    private Integer monthlyAmortizationDate;

    private Boolean uploadSuccessful;

    private Boolean uploadOrphaned;

    private String originatingFileToken;

    private String amortizationTag;

    public String originatingFileToken() {
        return this.originatingFileToken;
    }

    public IsTokenized<String> originatingFileToken(final String token) {
        this.originatingFileToken = token;
        return this;
    }
}
