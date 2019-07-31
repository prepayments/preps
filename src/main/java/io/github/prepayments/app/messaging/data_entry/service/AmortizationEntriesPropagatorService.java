package io.github.prepayments.app.messaging.data_entry.service;

import io.github.prepayments.app.messaging.filing.vm.AmortizationEntryEVM;
import io.github.prepayments.service.dto.AmortizationUploadDTO;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * This service creates from amortization-upload entities the constituent amortization-entry entities
 * that form the full amortization of a prepayment-entry entity
 */
public interface AmortizationEntriesPropagatorService {

    /**
     * Propagates the upload DTO into constituent amortization entries Default DateTimeFormatter is used : DateTimeFormatter.ofPattern("yyyy/MM/dd")
     *
     * @param amortizationUploadDTO The upload to be propagated into constituent amortization-entries
     * @param monthlyAmortizationDate Day of the month when amortization of prepayments is carried out...
     */
    List<AmortizationEntryEVM> propagateAmortizationEntries(final AmortizationUploadDTO amortizationUploadDTO, final int monthlyAmortizationDate);

    /**
     * Propagates the upload DTO into constituent amortization entries
     *
     * @param dtf                   DateTimeFormatter for converting date items back to formatted string
     * @param amortizationUploadDTO The upload to be propagated into constituent amortization-entries
     * @param monthlyAmortizationDate Day of the month when amortization of prepayments is carried out...
     */
    List<AmortizationEntryEVM> propagateAmortizationEntries(final DateTimeFormatter dtf, final AmortizationUploadDTO amortizationUploadDTO, final int monthlyAmortizationDate);
}
