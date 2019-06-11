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
public class TransactionAccountsGeneratedReportResource extends AbstractGeneratedListingReportResource implements ReportResponseGenerator {

    private static final String REPORT_TITLE = "All Transaction Accounts";

    private final ReportGeneratorService transactionAccountReportGeneratorService;
    private final ReportTypeResolver reportTitleTypeResolver;

    // @formatter:off
    @Autowired
    public TransactionAccountsGeneratedReportResource(final ReportTypeResolver reportTitleTypeResolver,
                                               final ReportGeneratorService transactionAccountReportGeneratorService,
                                               final ReportRequestEventService reportRequestEventService) {
        super(reportRequestEventService);

        this.transactionAccountReportGeneratorService = transactionAccountReportGeneratorService;
        this.reportTitleTypeResolver = reportTitleTypeResolver;
    }
    // @formatter:on


    @Override
    Optional<GeneratedReportDTO> generateReport() {

        return transactionAccountReportGeneratorService.generateReport(getReportTitle() + " Listing", getReportTitle());
    }

    /**
     * GET  /api/transaction-accounts-listing-report : get the transaction accounts entries report requested
     *
     * @return the ResponseEntity with status 200 (OK) and with body the reportRequestEventDTO, or with status 404 (Not Found)
     */
    @GetMapping("/transaction-accounts-listing-report")
    public ResponseEntity<GeneratedReportDTO> allTransactionAccountsListing() {

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
