package io.github.prepayments.repository;

import io.github.prepayments.domain.AmortizationDataEntryFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AmortizationDataEntryFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AmortizationDataEntryFileRepository extends JpaRepository<AmortizationDataEntryFile, Long>, JpaSpecificationExecutor<AmortizationDataEntryFile> {

}
