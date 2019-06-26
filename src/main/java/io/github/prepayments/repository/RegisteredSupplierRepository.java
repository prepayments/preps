package io.github.prepayments.repository;

import io.github.prepayments.domain.RegisteredSupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RegisteredSupplier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegisteredSupplierRepository extends JpaRepository<RegisteredSupplier, Long>, JpaSpecificationExecutor<RegisteredSupplier> {

}
