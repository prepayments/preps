package io.github.prepayments.repository;

import io.github.prepayments.domain.AmortizationUploadFile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AmortizationUploadFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AmortizationUploadFileRepository extends JpaRepository<AmortizationUploadFile, Long>, JpaSpecificationExecutor<AmortizationUploadFile> {

}
