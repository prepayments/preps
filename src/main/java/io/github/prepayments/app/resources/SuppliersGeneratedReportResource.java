package io.github.prepayments.app.resources;

import io.github.jhipster.service.filter.StringFilter;
import io.github.prepayments.app.models.GeneratedReportDTO;
import io.github.prepayments.app.services.reports.ReportGeneratorService;
import io.github.prepayments.domain.enumeration.ReportMediumTypes;
import io.github.prepayments.service.ReportRequestEventService;
import io.github.prepayments.service.dto.ReportTypeCriteria;
import io.github.prepayments.service.dto.ReportTypeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class SuppliersGeneratedReportResource extends AbstractGeneratedListingReportResource implements ReportResponseGenerator {

    private static final String REPORT_TITLE = "All Registered Suppliers";

    private final ReportGeneratorService suppliersReportGeneratorService;
    private final ReportTypeResolver reportTitleTypeResolver;

    // @formatter:off
    @Autowired
    public SuppliersGeneratedReportResource(final ReportTypeResolver reportTitleTypeResolver,
                                               final ReportGeneratorService suppliersReportGeneratorService,
                                               final ReportRequestEventService reportRequestEventService) {
        super(reportRequestEventService);

        this.suppliersReportGeneratorService = suppliersReportGeneratorService;
        this.reportTitleTypeResolver = reportTitleTypeResolver;
    }
    // @formatter:on


    @Override
    Optional<GeneratedReportDTO> generateReport() {

        return suppliersReportGeneratorService.generateReport(getReportTitle() + " Listing", getReportTitle());
    }

    /**
     * GET  /api/registered-suppliers-listing-report : get the registered supplier entries report requested
     *
     * @return the ResponseEntity with status 200 (OK) and with body the reportRequestEventDTO, or with status 404 (Not Found)
     */
    @GetMapping("/registered-suppliers-listing-report")
    public ResponseEntity<GeneratedReportDTO> allRegisteredSuppliersListing() {

        return super.generatedReportResponseEntity();
    }

    @Override
    String getReportTitle() {
        return REPORT_TITLE;
    }

    @Override
    ReportTypeDTO getReportType() {

        ReportTypeCriteria reportTypeCriteria = new ReportTypeCriteria();
        reportTypeCriteria.setReportModelName(new StringFilter().setContains(getReportTitle()));

        return reportTitleTypeResolver.getReportTypeDTOS(reportTypeCriteria, ReportMediumTypes.EXCEL).get(0);
    }
}
