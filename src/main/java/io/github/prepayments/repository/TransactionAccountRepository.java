package io.github.prepayments.repository;

import io.github.prepayments.domain.TransactionAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TransactionAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionAccountRepository extends JpaRepository<TransactionAccount, Long>, JpaSpecificationExecutor<TransactionAccount> {

}
