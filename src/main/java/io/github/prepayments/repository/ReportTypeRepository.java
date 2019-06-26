package io.github.prepayments.repository;

import io.github.prepayments.domain.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ReportType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReportTypeRepository extends JpaRepository<ReportType, Long>, JpaSpecificationExecutor<ReportType> {

    ReportType findFirstByReportModelName(String reportModelName);
}
