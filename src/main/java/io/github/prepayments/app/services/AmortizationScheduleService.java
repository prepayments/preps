package io.github.prepayments.app.services;

import io.github.prepayments.app.models.AmortizationScheduleDTO;

import java.util.List;

/**
 * This interface creates a listing of amortization schedule items given certain parameters
 */
public interface AmortizationScheduleService<Q> {

    List<AmortizationScheduleDTO> scheduleAmortization(Q queryParameters);
}
