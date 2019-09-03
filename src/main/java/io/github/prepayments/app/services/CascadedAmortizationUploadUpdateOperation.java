package io.github.prepayments.app.services;

import io.github.prepayments.app.messaging.data_entry.service.IPrepaymentEntryIdService;
import io.github.prepayments.service.AmortizationEntryQueryService;
import io.github.prepayments.service.AmortizationEntryService;
import io.github.prepayments.service.dto.AmortizationEntryCriteria;
import io.github.prepayments.service.dto.AmortizationEntryDTO;
import io.github.prepayments.service.dto.AmortizationUploadDTO;
import io.github.prepayments.web.rest.errors.BadRequestAlertException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static io.github.prepayments.app.AppConstants.DATETIME_FORMATTER;

@Slf4j
@Service
@Transactional
public class CascadedAmortizationUploadUpdateOperation implements CascadeUpdateOperation<AmortizationUploadDTO> {

    private final AmortizationEntryService amortizationEntryService;
    private final AmortizationEntryQueryService amortizationEntryQueryService;
    private final IPrepaymentEntryIdService failSafePrepaymentIdService;
    private final CriteriaUpdate<AmortizationUploadDTO, AmortizationEntryCriteria> amortizationUploadAmortizationEntryCriteriaUpdate;

    public CascadedAmortizationUploadUpdateOperation(final AmortizationEntryService amortizationEntryService, final AmortizationEntryQueryService amortizationEntryQueryService,
                                                     final IPrepaymentEntryIdService failSafePrepaymentIdService,
                                                     final CriteriaUpdate<AmortizationUploadDTO, AmortizationEntryCriteria> amortizationUploadAmortizationEntryCriteriaUpdate) {
        this.amortizationEntryService = amortizationEntryService;
        this.amortizationEntryQueryService = amortizationEntryQueryService;
        this.failSafePrepaymentIdService = failSafePrepaymentIdService;
        this.amortizationUploadAmortizationEntryCriteriaUpdate = amortizationUploadAmortizationEntryCriteriaUpdate;
    }

    @Override
    public void cascade(final CascadedOperation cascadedOperation, final AmortizationUploadDTO cascadable) {

        AmortizationEntryCriteria amortizationEntryCriteria = new AmortizationEntryCriteria();

        // update the criteria
        amortizationUploadAmortizationEntryCriteriaUpdate.updateCriteria(cascadable, amortizationEntryCriteria);

        List<AmortizationEntryDTO> amortizationEntries = amortizationEntryQueryService.findByCriteria(amortizationEntryCriteria);
        log.info("Updating {} amortization entries", amortizationEntries.size());

        amortizationEntries.forEach(entry -> {
            if (entry.getId() == null) {
                throw new BadRequestAlertException("Invalid id", "CascadedAmortizationUploadUpdateOperation", "idnull");
            }
            // Update entries
            entry.setParticulars(cascadable.getParticulars());
            entry.setAmortizationServiceOutlet(cascadable.getAmortizationServiceOutletCode());
            //entry.setPrepaymentServiceOutlet(cascadable.getPrepaymentServiceOutletCode()); // needless
            //entry.setPrepaymentAccountNumber(cascadable.getPrepaymentAccountNumber()); // needless
            entry.setAmortizationAccountNumber(cascadable.getExpenseAccountNumber());
            entry.setPrepaymentEntryId(
                failSafePrepaymentIdService.findByIdAndDate(entry, cascadable.getPrepaymentTransactionId(), DATETIME_FORMATTER.format(cascadable.getPrepaymentTransactionDate())));
            entry.setAmortizationAmount(cascadable.getAmortizationAmount());
            entry.setAmortizationTag(cascadable.getAmortizationTag());
            entry.setOriginatingFileToken(cascadable.getOriginatingFileToken());
            //TODO Change day of month of amortization
            entry.setAmortizationDate(entry.getAmortizationDate().withDayOfMonth(cascadable.getMonthlyAmortizationDate()));

            amortizationEntryService.save(entry);
        });
    }
}
