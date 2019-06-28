package io.github.prepayments.app.messaging.data_entry.service;

import io.github.prepayments.service.dto.AmortizationUploadDTO;

import java.time.format.DateTimeFormatter;

public interface AmortizationEntriesPropagatorService {

    /**
     * Propagates the upload DTO into constituent amortization entries Default DateTimeFormatter is used : DateTimeFormatter.ofPattern("yyyy/MM/dd")
     *
     * @param amortizationUploadDTO The upload to be propagated into constituent amortization-entries
     */
    void propagateAmortizationEntries(AmortizationUploadDTO amortizationUploadDTO);

    /**
     * Propagates the upload DTO into constituent amortization entries
     *
     * @param dtf                   DateTimeFormatter for converting date items back to formatted string
     * @param amortizationUploadDTO The upload to be propagated into constituent amortization-entries
     */
    void propagateAmortizationEntries(final DateTimeFormatter dtf, final AmortizationUploadDTO amortizationUploadDTO);
}
