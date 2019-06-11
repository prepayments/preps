package io.github.prepayments.app.messaging.data_entry.service;

public interface IPrepaymentEntryIdService {
    /**
     * This method return the DB domain Id whose date and business Id is as given
     */
    Long findByIdAndDate(String prepaymentEntryId, String prepaymentEntryDate);
}
