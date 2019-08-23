package io.github.prepayments.repository;

import io.github.prepayments.domain.PrepaymentDataEntryFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PrepaymentDataEntryFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrepaymentDataEntryFileRepository extends JpaRepository<PrepaymentDataEntryFile, Long>, JpaSpecificationExecutor<PrepaymentDataEntryFile> {

}
