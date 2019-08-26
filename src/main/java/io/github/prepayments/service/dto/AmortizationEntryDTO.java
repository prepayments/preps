package io.github.prepayments.service.dto;

import io.github.prepayments.app.token.TagCapable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A DTO for the {@link io.github.prepayments.domain.AmortizationEntry} entity.
 */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AmortizationEntryDTO implements Serializable, TagCapable<String> {

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

    private String originatingFileToken;

    private String amortizationTag;

    private Boolean orphaned;

    private Long prepaymentEntryId;
}
