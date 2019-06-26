package io.github.prepayments.repository;

import io.github.prepayments.domain.ServiceOutlet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ServiceOutlet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceOutletRepository extends JpaRepository<ServiceOutlet, Long>, JpaSpecificationExecutor<ServiceOutlet> {

}
