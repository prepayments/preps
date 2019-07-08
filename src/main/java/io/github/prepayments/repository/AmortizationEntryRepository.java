package io.github.prepayments.repository;

import io.github.prepayments.domain.AmortizationEntry;
import io.github.prepayments.domain.PrepaymentEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the AmortizationEntry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AmortizationEntryRepository extends JpaRepository<AmortizationEntry, Long>, JpaSpecificationExecutor<AmortizationEntry> {

    List<AmortizationEntry> findAllByPrepaymentEntryIs(PrepaymentEntry prepaymentEntry);
}
