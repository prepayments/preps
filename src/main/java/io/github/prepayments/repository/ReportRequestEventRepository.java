package io.github.prepayments.repository;

import io.github.prepayments.domain.ReportRequestEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ReportRequestEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReportRequestEventRepository extends JpaRepository<ReportRequestEvent, Long>, JpaSpecificationExecutor<ReportRequestEvent> {

}
