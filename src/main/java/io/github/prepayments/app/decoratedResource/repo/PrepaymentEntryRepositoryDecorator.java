package io.github.prepayments.app.decoratedResource.repo;

import io.github.prepayments.domain.PrepaymentEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

/**
 * Spring Data  repository for the PrepaymentEntry entity decorated with custom methods
 */
@SuppressWarnings("unused")
@Repository("prepaymentEntryRepositoryDecorator")
public interface PrepaymentEntryRepositoryDecorator extends JpaRepository<PrepaymentEntry, Long>, JpaSpecificationExecutor<PrepaymentEntry> {

    PrepaymentEntry findFirstByPrepaymentIdAndPrepaymentDate(String prepaymentEntryId, LocalDate prepaymentDate);
}
