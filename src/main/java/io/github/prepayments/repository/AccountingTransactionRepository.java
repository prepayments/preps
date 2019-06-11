package io.github.prepayments.repository;

import io.github.prepayments.domain.AccountingTransaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AccountingTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountingTransactionRepository extends JpaRepository<AccountingTransaction, Long>, JpaSpecificationExecutor<AccountingTransaction> {

}
