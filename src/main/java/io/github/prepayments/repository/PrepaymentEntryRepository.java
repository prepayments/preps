package io.github.prepayments.repository;

import io.github.prepayments.app.messaging.notifications.dto.FileUploadNotification;
import io.github.prepayments.domain.PrepaymentEntry;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


/**
 * Spring Data  repository for the PrepaymentEntry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrepaymentEntryRepository extends JpaRepository<PrepaymentEntry, Long>, JpaSpecificationExecutor<PrepaymentEntry> {

    PrepaymentEntry findFirstByPrepaymentIdAndPrepaymentDate(String prepaymentEntryId, LocalDate prepaymentDate);
}
