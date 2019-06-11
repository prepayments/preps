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
public class AmortizationGeneratedReportResource extends AbstractGeneratedListingReportResource implements ReportResponseGenerator {

    private static final String REPORT_TITLE = "All Amortization Entries";

    private final ReportGeneratorService amortizationReportGeneratorService;
    private final ReportTypeResolver reportTitleTypeResolver;

    // @formatter:off
    @Autowired
    public AmortizationGeneratedReportResource(final ReportTypeResolver reportTitleTypeResolver,
                                               final ReportGeneratorService amortizationReportGeneratorService,
                                               final ReportRequestEventService reportRequestEventService) {
        super(reportRequestEventService);

        this.amortizationReportGeneratorService = amortizationReportGeneratorService;
        this.reportTitleTypeResolver = reportTitleTypeResolver;
    }
    // @formatter:on


    /**
     * GET  /api/amortization-entries-report : get the amortization entries report requested
     *
     * @return the ResponseEntity with status 200 (OK) and with body the reportRequestEventDTO, or with status 404 (Not Found)
     */
    @GetMapping("/amortization-entries-report")
    public ResponseEntity<GeneratedReportDTO> allAmortizationEntriesReport() {

        log.debug("Generating response entity for report entitled : {}", REPORT_TITLE);

        return super.generatedReportResponseEntity();
    }

    @Override
    Optional<GeneratedReportDTO> generateReport() {

        return amortizationReportGeneratorService.generateReport(REPORT_TITLE + " Listing",REPORT_TITLE);
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
