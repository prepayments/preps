package io.github.prepayments.repository;

import io.github.prepayments.domain.TransactionAccountDataEntryFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TransactionAccountDataEntryFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionAccountDataEntryFileRepository extends JpaRepository<TransactionAccountDataEntryFile, Long>, JpaSpecificationExecutor<TransactionAccountDataEntryFile> {

}
