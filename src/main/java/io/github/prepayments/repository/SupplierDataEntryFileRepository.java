package io.github.prepayments.repository;

import io.github.prepayments.domain.SupplierDataEntryFile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SupplierDataEntryFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupplierDataEntryFileRepository extends JpaRepository<SupplierDataEntryFile, Long>, JpaSpecificationExecutor<SupplierDataEntryFile> {

}
