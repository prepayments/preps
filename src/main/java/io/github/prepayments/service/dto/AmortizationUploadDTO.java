package io.github.prepayments.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link io.github.prepayments.domain.AmortizationUpload} entity.
 */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AmortizationUploadDTO implements Serializable {

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

    @Pattern(regexp = "^[1-2]?[0-8]$")
    private String monthlyAmortizationDate;

    private Boolean uploadSuccessful;

    private Boolean uploadOrphaned;

    private String originatingFileToken;
}
