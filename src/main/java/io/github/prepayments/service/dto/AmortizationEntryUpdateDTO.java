package io.github.prepayments.service.dto;

import io.github.prepayments.app.messaging.data_entry.updates.InstallableUpdate;
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
 * A DTO for the {@link io.github.prepayments.domain.AmortizationEntryUpdate} entity.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
@Accessors(chain = true)
public class AmortizationEntryUpdateDTO implements Serializable, InstallableUpdate<Long, AmortizationEntryUpdateDTO> {

    private Long id;

    @NotNull
    private Long amortizationEntryId;

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

    @NotNull
    private Long prepaymentEntryId;

    private String originatingFileToken;

    private String amortizationTag;

    private Boolean orphaned;

    @Override
    public Long getEntryId() {
        return amortizationEntryId;
    }

    @Override
    public AmortizationEntryUpdateDTO getEntity() {
        return this;
    }
}
