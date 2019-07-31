package io.github.prepayments.service.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link io.github.prepayments.domain.AmortizationEntry} entity.
 */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AmortizationEntryDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate amortizationDate;

    @NotNull
    private BigDecimal amortizationAmount;

    private String particulars;

    @NotNull
    @Pattern(regexp = "^[0-9]{3}$")
    private String prepaymentServiceOutlet;

    @NotNull
    private String prepaymentAccountNumber;

    @NotNull
    @Pattern(regexp = "^[0-9]{3}$")
    private String amortizationServiceOutlet;

    @NotNull
    private String amortizationAccountNumber;

    private String OriginatingFileToken;


    private Long prepaymentEntryId;
}
