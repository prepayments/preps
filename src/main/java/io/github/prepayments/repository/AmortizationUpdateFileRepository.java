package io.github.prepayments.repository;

import io.github.prepayments.domain.AmortizationUpdateFile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AmortizationUpdateFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AmortizationUpdateFileRepository extends JpaRepository<AmortizationUpdateFile, Long>, JpaSpecificationExecutor<AmortizationUpdateFile> {

}
