package io.github.prepayments.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    private Boolean uploadSuccessful;

    private Boolean uploadOrphaned;

    private String originatingFileToken;
}
