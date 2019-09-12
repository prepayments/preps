package io.github.prepayments.app.services.reports;

import io.github.prepayments.app.models.AmortizationScheduleDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service("amortizationSchedulePrepaymentBalanceService")
public class AmortizationSchedulePrepaymentBalanceService implements PrepaymentBalanceService<AmortizationScheduleDTO> {


    /**
     * Calculate balance given the paremeters
     */
    @Override
    public BigDecimal getBalance(final AmortizationScheduleDTO queryParams) {
        // TODO this...
        return BigDecimal.ZERO;
    }
}
