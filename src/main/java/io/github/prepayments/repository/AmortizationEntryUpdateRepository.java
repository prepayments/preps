package io.github.prepayments.repository;

import io.github.prepayments.domain.AmortizationEntryUpdate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AmortizationEntryUpdate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AmortizationEntryUpdateRepository extends JpaRepository<AmortizationEntryUpdate, Long>, JpaSpecificationExecutor<AmortizationEntryUpdate> {

}
