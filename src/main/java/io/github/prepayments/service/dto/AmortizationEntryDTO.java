package io.github.prepayments.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A DTO for the {@link io.github.prepayments.domain.AmortizationEntry} entity.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class AmortizationEntryDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate amortizationDate;

    @NotNull
    private BigDecimal amortizationAmount;

    private String particulars;

    private Boolean posted;

    @NotNull
    @Pattern(regexp = "^[0-9]{3}$")
    private String serviceOutlet;

    @NotNull
    @Pattern(regexp = "^[0-9]{10,16}$")
    private String accountNumber;

    @NotNull
    private String accountName;


    private Long prepaymentEntryId;
}
