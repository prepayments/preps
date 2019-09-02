package io.github.prepayments.app.services;

import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.prepayments.app.messaging.data_entry.service.IPrepaymentEntryIdService;
import io.github.prepayments.service.AmortizationEntryQueryService;
import io.github.prepayments.service.dto.AmortizationEntryCriteria;
import io.github.prepayments.service.dto.AmortizationUploadDTO;
import org.springframework.stereotype.Service;

import static io.github.prepayments.app.AppConstants.DATETIME_FORMATTER;

/**
 * This class updates the AmortizationEntry criteria using data from an amortizationUploadDTO as source
 */
@Service("amortizationUploadAmortizationEntryCriteriaUpdate")
public class AmortizationUploadAmortizationEntryCriteriaUpdate implements CriteriaUpdate<AmortizationUploadDTO, AmortizationEntryCriteria> {

    private final AmortizationEntryQueryService amortizationEntryQueryService;
    private final IPrepaymentEntryIdService prepaymentEntryIdService;

    public AmortizationUploadAmortizationEntryCriteriaUpdate(final AmortizationEntryQueryService amortizationEntryQueryService, final IPrepaymentEntryIdService prepaymentEntryIdService) {
        this.amortizationEntryQueryService = amortizationEntryQueryService;
        this.prepaymentEntryIdService = prepaymentEntryIdService;
    }

    @Override
    public void updateCriteria(final AmortizationUploadDTO amortizationUploadDTO, final AmortizationEntryCriteria amortizationEntryCriteria) {
        if (amortizationUploadDTO.getAmortizationTag() != null) {
            if (!amortizationUploadDTO.getAmortizationTag().isEmpty()) {
                StringFilter amortizationTag = new StringFilter();
                amortizationTag.setEquals(amortizationUploadDTO.getAmortizationTag());
                amortizationEntryCriteria.setAmortizationTag(amortizationTag);
            }
        } else {
            StringFilter fileToken = new StringFilter();
            fileToken.setEquals(amortizationUploadDTO.getOriginatingFileToken());
            amortizationEntryCriteria.setOriginatingFileToken(fileToken);

            LongFilter prepaymentEntryId = new LongFilter();
            prepaymentEntryId.setEquals(
                prepaymentEntryIdService.findByIdAndDate(amortizationEntryQueryService.findByCriteria(amortizationEntryCriteria).get(0), amortizationUploadDTO.getPrepaymentTransactionId(),
                                                         DATETIME_FORMATTER.format(amortizationUploadDTO.getPrepaymentTransactionDate())));
            amortizationEntryCriteria.setPrepaymentEntryId(prepaymentEntryId);

        }
    }
}
