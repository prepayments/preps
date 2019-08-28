package io.github.prepayments.service.dto;

import io.github.prepayments.app.token.TagCapable;
import io.github.prepayments.app.token.TagCapableAmortizationModel;
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

/**
 * A DTO for the {@link io.github.prepayments.domain.AmortizationEntry} entity.
 */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AmortizationEntryDTO implements Serializable, TagCapable<String>, TagCapableAmortizationModel {

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

    private Long prepaymentEntryId;
}
