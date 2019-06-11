package io.github.prepayments.app.resources;

import io.github.jhipster.service.filter.StringFilter;
import io.github.prepayments.app.models.GeneratedReportDTO;
import io.github.prepayments.app.services.reports.ReportGeneratorService;
import io.github.prepayments.domain.enumeration.ReportMediumTypes;
import io.github.prepayments.service.ReportRequestEventService;
import io.github.prepayments.service.dto.ReportTypeCriteria;
import io.github.prepayments.service.dto.ReportTypeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
public class ServiceOutletGeneratedReportResource extends AbstractGeneratedListingReportResource implements ReportResponseGenerator {

    private static final String REPORT_TITLE = "All Service Outlets";

    private final ReportGeneratorService serviceOutletReportGeneratorService;
    private final ReportTypeResolver reportTitleTypeResolver;

    // @formatter:off
    @Autowired
    public ServiceOutletGeneratedReportResource(final ReportTypeResolver reportTitleTypeResolver,
                                               final ReportGeneratorService serviceOutletReportGeneratorService,
                                               final ReportRequestEventService reportRequestEventService) {
        super(reportRequestEventService);

        this.serviceOutletReportGeneratorService = serviceOutletReportGeneratorService;
        this.reportTitleTypeResolver = reportTitleTypeResolver;
    }
    // @formatter:on


    @Override
    Optional<GeneratedReportDTO> generateReport() {

        return serviceOutletReportGeneratorService.generateReport(getReportTitle() + " Listing", getReportTitle());
    }

    /**
     * GET  /api/service-outlets-listing-report : get the service outlet entries report requested
     *
     * @return the ResponseEntity with status 200 (OK) and with body the reportRequestEventDTO, or with status 404 (Not Found)
     */
    @GetMapping("/service-outlets-listing-report")
    public ResponseEntity<GeneratedReportDTO> allServiceOutletsListing() {

        log.debug("Generating response entity for generated report of the title : {}", REPORT_TITLE);

        return super.generatedReportResponseEntity();
    }

    @Override
    String getReportTitle() {
        return REPORT_TITLE;
    }

    @Override
    ReportTypeDTO getReportType() {

        ReportTypeCriteria reportTypeCriteria = new ReportTypeCriteria();
        reportTypeCriteria.setReportModelName(new StringFilter().setContains(REPORT_TITLE));

        return reportTitleTypeResolver.getReportTypeDTOS(reportTypeCriteria, ReportMediumTypes.EXCEL).get(0);
    }
}
