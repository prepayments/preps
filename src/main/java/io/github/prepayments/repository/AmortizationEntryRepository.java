package io.github.prepayments.repository;

import io.github.prepayments.domain.AmortizationEntry;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AmortizationEntry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AmortizationEntryRepository extends JpaRepository<AmortizationEntry, Long>, JpaSpecificationExecutor<AmortizationEntry> {

}
