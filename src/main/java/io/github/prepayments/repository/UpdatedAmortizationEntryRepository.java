package io.github.prepayments.repository;

import io.github.prepayments.domain.UpdatedAmortizationEntry;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UpdatedAmortizationEntry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UpdatedAmortizationEntryRepository extends JpaRepository<UpdatedAmortizationEntry, Long>, JpaSpecificationExecutor<UpdatedAmortizationEntry> {

}
