package io.github.prepayments.repository;

import io.github.prepayments.domain.ServiceOutletDataEntryFile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ServiceOutletDataEntryFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceOutletDataEntryFileRepository extends JpaRepository<ServiceOutletDataEntryFile, Long>, JpaSpecificationExecutor<ServiceOutletDataEntryFile> {

}
