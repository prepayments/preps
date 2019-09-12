package io.github.prepayments.app.services.reports;

import io.github.prepayments.app.models.AmortizationScheduleDTO;

import java.math.BigDecimal;

/**
 * This interface calculates the balance amount for a prepayment
 */
public interface PrepaymentBalanceService<T> {

    /**
     * Calculate balance given the paremeters
     * @param queryParams
     * @return
     */
    BigDecimal getBalance(T queryParams);
}
