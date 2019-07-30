package io.github.prepayments.repository;

import io.github.prepayments.domain.AmortizationUpload;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AmortizationUpload entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AmortizationUploadRepository extends JpaRepository<AmortizationUpload, Long>, JpaSpecificationExecutor<AmortizationUpload> {

}
