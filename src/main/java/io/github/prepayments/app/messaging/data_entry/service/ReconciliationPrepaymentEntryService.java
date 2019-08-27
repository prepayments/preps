package io.github.prepayments.app.messaging.data_entry.service;

import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.prepayments.service.PrepaymentEntryQueryService;
import io.github.prepayments.service.PrepaymentEntryService;
import io.github.prepayments.service.dto.PrepaymentEntryCriteria;
import io.github.prepayments.service.dto.PrepaymentEntryDTO;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import static io.github.prepayments.app.AppConstants.PREPAYMENT_RECONCILIATION_ACCOUNT_NAME;
import static io.github.prepayments.app.AppConstants.PREPAYMENT_RECONCILIATION_ACCOUNT_NUMBER;
import static io.github.prepayments.app.AppConstants.PREPAYMENT_RECONCILIATION_AMOUNT;
import static io.github.prepayments.app.AppConstants.PREPAYMENT_RECONCILIATION_ID;
import static io.github.prepayments.app.AppConstants.PREPAYMENT_RECONCILIATION_LOCAL_DATE;
import static io.github.prepayments.app.AppConstants.PREPAYMENT_RECONCILIATION_PARTICULARS;
import static io.github.prepayments.app.AppConstants.PREPAYMENT_RECONCILIATION_SERVICE_OUTLET;

/**
 * Creates a default reconciliation prepayment entry at application startup
 */
@Component("reconciliationPrepaymentEntryService")
public class ReconciliationPrepaymentEntryService implements ApplicationRunner {

    private final PrepaymentEntryService prepaymentEntryService;
    private final PrepaymentEntryQueryService prepaymentEntryQueryService;

    public ReconciliationPrepaymentEntryService(final PrepaymentEntryService prepaymentEntryService, final PrepaymentEntryQueryService prepaymentEntryQueryService) {
        this.prepaymentEntryService = prepaymentEntryService;
        this.prepaymentEntryQueryService = prepaymentEntryQueryService;
    }

    @Override
    public void run(final ApplicationArguments args) throws Exception {

        if (!exists()) {
            // @formatter:off
        prepaymentEntryService.save(PrepaymentEntryDTO.builder()
                                                      .accountNumber(PREPAYMENT_RECONCILIATION_ACCOUNT_NUMBER)
                                                      .accountName(PREPAYMENT_RECONCILIATION_ACCOUNT_NAME)
                                                      .prepaymentId(PREPAYMENT_RECONCILIATION_ID)
                                                      .prepaymentDate(PREPAYMENT_RECONCILIATION_LOCAL_DATE)
                                                      .particulars(PREPAYMENT_RECONCILIATION_PARTICULARS)
                                                      .serviceOutlet(PREPAYMENT_RECONCILIATION_SERVICE_OUTLET)
                                                      .prepaymentAmount(PREPAYMENT_RECONCILIATION_AMOUNT)
                                                      .build());
        // @formatter:on
        }

    }

    /**
     * @return True is the reconciliation prepayment transaction exists
     */
    private boolean exists() {

        StringFilter prepaymentEntryIdFilter = new StringFilter();
        prepaymentEntryIdFilter.setContains(PREPAYMENT_RECONCILIATION_ID);
        LocalDateFilter prepaymentEntryDateFilter = new LocalDateFilter();
        prepaymentEntryDateFilter.setEquals(PREPAYMENT_RECONCILIATION_LOCAL_DATE);


        PrepaymentEntryCriteria reconciliationPrepaymentEntryFilter = new PrepaymentEntryCriteria();
        reconciliationPrepaymentEntryFilter.setPrepaymentId(prepaymentEntryIdFilter);
        reconciliationPrepaymentEntryFilter.setPrepaymentDate(prepaymentEntryDateFilter);

        return !prepaymentEntryQueryService.findByCriteria(reconciliationPrepaymentEntryFilter).isEmpty();

    }
}
