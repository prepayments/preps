package io.github.prepayments.service.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link io.github.prepayments.domain.UpdatedAmortizationEntry} entity.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
@Accessors(chain = true)
public class UpdatedAmortizationEntryDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate amortizationDate;

    @NotNull
    private BigDecimal amortizationAmount;

    private String particulars;

    @NotNull
    private String prepaymentServiceOutlet;

    @NotNull
    private String prepaymentAccountNumber;

    @NotNull
    private String amortizationServiceOutlet;

    @NotNull
    private String amortizationAccountNumber;

    private String originatingFileToken;

    private String amortizationTag;

    private Boolean orphaned;

    @NotNull
    private LocalDate dateOfUpdate;

    private String reasonForUpdate;


    private Long prepaymentEntryId;
}
