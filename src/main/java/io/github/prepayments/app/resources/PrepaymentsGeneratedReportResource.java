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
public class PrepaymentsGeneratedReportResource extends AbstractGeneratedListingReportResource implements ReportResponseGenerator {

    private static final String REPORT_TITLE = "All Prepayment Entries";

    //    private final ReportGeneratorService amortizationReportGeneratorService;
    private final ReportTypeResolver reportTitleTypeResolver;
    private final ReportGeneratorService prepaymentReportGeneratorService;

    // @formatter:off
    @Autowired
    public PrepaymentsGeneratedReportResource(final ReportTypeResolver reportTitleTypeResolver,
                                               final ReportGeneratorService prepaymentReportGeneratorService,
                                               final ReportRequestEventService reportRequestEventService) {
        super(reportRequestEventService);

        this.prepaymentReportGeneratorService = prepaymentReportGeneratorService;
        this.reportTitleTypeResolver = reportTitleTypeResolver;
    }
    // @formatter:on

    /**
     * GET  /api/prepayment-entries-report : get the prepayment entries report requested
     *
     * @return the ResponseEntity with status 200 (OK) and with body the reportRequestEventDTO, or with status 404 (Not Found)
     */
    @GetMapping("/prepayment-entries-report")
    public ResponseEntity<GeneratedReportDTO> allPrepaymentEntriesListingReport() {


        return super.generatedReportResponseEntity();
    }

    @Override
    Optional<GeneratedReportDTO> generateReport() {

        return prepaymentReportGeneratorService.generateReport(REPORT_TITLE + " Listing", REPORT_TITLE);
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
