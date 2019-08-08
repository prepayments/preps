package io.github.prepayments.app.messaging.data_entry.service;

import io.github.prepayments.service.dto.AmortizationEntryDTO;

public interface IPrepaymentEntryIdService {
    /**
     * This method return the DB domain Id whose date and business Id is as given
     */
    Long findByIdAndDate(final AmortizationEntryDTO amortizationEntryDTO, String prepaymentEntryId, String prepaymentEntryDate);
}
