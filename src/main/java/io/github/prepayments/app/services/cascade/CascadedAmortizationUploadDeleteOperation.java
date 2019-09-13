package io.github.prepayments.app.services.cascade;

import io.github.prepayments.app.services.criteria.CriteriaUpdate;
import io.github.prepayments.service.AmortizationEntryQueryService;
import io.github.prepayments.service.AmortizationEntryService;
import io.github.prepayments.service.dto.AmortizationEntryCriteria;
import io.github.prepayments.service.dto.AmortizationEntryDTO;
import io.github.prepayments.service.dto.AmortizationUploadDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CascadedAmortizationUploadDeleteOperation implements CascadeDeleteOperation<AmortizationUploadDTO> {

    private final AmortizationEntryQueryService amortizationEntryQueryService;
    private final AmortizationEntryService amortizationEntryService;
    private final CriteriaUpdate<AmortizationUploadDTO, AmortizationEntryCriteria> amortizationUploadAmortizationEntryCriteriaUpdate;


    public CascadedAmortizationUploadDeleteOperation(final AmortizationEntryQueryService amortizationEntryQueryService, final AmortizationEntryService amortizationEntryService,
                                                     final CriteriaUpdate<AmortizationUploadDTO, AmortizationEntryCriteria> amortizationUploadAmortizationEntryCriteriaUpdate) {
        this.amortizationEntryQueryService = amortizationEntryQueryService;
        this.amortizationEntryService = amortizationEntryService;
        this.amortizationUploadAmortizationEntryCriteriaUpdate = amortizationUploadAmortizationEntryCriteriaUpdate;
    }

    @Override
    public void cascade(final CascadedOperation cascadedOperation, final AmortizationUploadDTO cascadable) {

        AmortizationEntryCriteria amortizationEntryCriteria = new AmortizationEntryCriteria();

        amortizationUploadAmortizationEntryCriteriaUpdate.updateCriteria(cascadable, amortizationEntryCriteria);

        List<AmortizationEntryDTO> amortizationEntries = amortizationEntryQueryService.findByCriteria(amortizationEntryCriteria);

        // Delete everything
        amortizationEntries.forEach(entry -> {
            amortizationEntryService.delete(entry.getId());
        });
    }

}
