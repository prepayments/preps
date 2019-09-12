package io.github.prepayments.app.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AmortizationScheduleDTO {

    private long prepaymentEntryId;
    private long amortizationEntryId;
    private String prepaymentAccount;
    private String amortizationAccount;
    private String particulars;
    private BigDecimal amount;
}
