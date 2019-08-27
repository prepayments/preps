package io.github.prepayments.app.messaging.data_entry.service;

import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.prepayments.service.PrepaymentEntryQueryService;
import io.github.prepayments.service.dto.AmortizationEntryDTO;
import io.github.prepayments.service.dto.PrepaymentEntryCriteria;
import io.github.prepayments.service.dto.PrepaymentEntryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static io.github.prepayments.app.AppConstants.DATETIME_FORMATTER;
import static io.github.prepayments.app.AppConstants.PREPAYMENT_RECONCILIATION_DATE;
import static io.github.prepayments.app.AppConstants.PREPAYMENT_RECONCILIATION_ID;

/**
 * This typical implementation of the prepayment-entry-id service makes a presumption to assign an id of 1l if the prepayment item being entered does not exist. Now there are many reasons why that
 * would make the application more brittle than robust, more is the sorrow due to the unfortunate use of a decorated repository. This implementation will search for the appropriate prepayment using
 * the criteria pattern so deftly provided by JPA. Should there be lack of a prepayment to assign, an id will be identified, at least one that points to a reconciliation prepayment entry
 */
@Transactional
@Service("failSafePrepaymentIdService")
@Slf4j
public class FailSafePrepaymentEntryIdService implements IPrepaymentEntryIdService {

    private final PrepaymentEntryQueryService prepaymentEntryQueryService;
    //    private final PrepaymentEntryMapper prepaymentEntryMapper;

    public FailSafePrepaymentEntryIdService(final PrepaymentEntryQueryService prepaymentEntryQueryService) {
        this.prepaymentEntryQueryService = prepaymentEntryQueryService;
    }

    /**
     * This method return the DB domain Id whose date and business Id is as given
     */
    @Override
    @Cacheable("prepaymentByIdAndDate")
    public Long findByIdAndDate(final AmortizationEntryDTO amortizationEntryDTO, final String prepaymentEntryId, final String prepaymentEntryDate) {

        Long findByIdAndDate;
        log.debug("Finding prepayment with the Id : {} dated : {}", prepaymentEntryId, prepaymentEntryDate);

        StringFilter prepaymentIdFilter = new StringFilter();
        prepaymentIdFilter.setEquals(prepaymentEntryId);
        LocalDateFilter prepaymentEntryDateFilter = new LocalDateFilter();
        prepaymentEntryDateFilter.setEquals(LocalDate.parse(prepaymentEntryDate, DATETIME_FORMATTER));

        PrepaymentEntryCriteria prepaymentIdCriteria = new PrepaymentEntryCriteria();
        prepaymentIdCriteria.setPrepaymentId(prepaymentIdFilter);
        prepaymentIdCriteria.setPrepaymentDate(prepaymentEntryDateFilter);

        List<PrepaymentEntryDTO> foundEntries = prepaymentEntryQueryService.findByCriteria(prepaymentIdCriteria);

        PrepaymentEntryDTO found = null;

        if (!foundEntries.isEmpty()) {
            found = foundEntries.get(0);
        }

        if (foundEntries.isEmpty()) {
            log.debug("Prepayment Not found: Prepayment Id : {}, dated : {}", prepaymentEntryId, prepaymentEntryDate);
            amortizationEntryDTO.setOrphaned(true);

            StringFilter prepaymentIdReconciliationFilter = new StringFilter();
            prepaymentIdReconciliationFilter.setEquals(PREPAYMENT_RECONCILIATION_ID);
            LocalDateFilter prepaymentEntryDateReconciliationFilter = new LocalDateFilter();
            prepaymentEntryDateReconciliationFilter.setEquals(LocalDate.parse(PREPAYMENT_RECONCILIATION_DATE, DATETIME_FORMATTER));

            PrepaymentEntryCriteria prepaymentIdReconCriteria = new PrepaymentEntryCriteria();
            prepaymentIdCriteria.setPrepaymentId(prepaymentIdReconciliationFilter);
            prepaymentIdCriteria.setPrepaymentDate(prepaymentEntryDateReconciliationFilter);

            List<PrepaymentEntryDTO> foundReconciliationEntries = prepaymentEntryQueryService.findByCriteria(prepaymentIdReconCriteria);

            PrepaymentEntryDTO foundReconciliation = null;

            if (!foundReconciliationEntries.isEmpty()) {
                foundReconciliation = foundReconciliationEntries.get(0);
                log.debug("Prepayment id {} for amortization entry prepayment id {}, dated {}",foundReconciliation.getId(), prepaymentEntryId, prepaymentEntryDate);
            } else {
                throw new RuntimeException("Reconciliation prepayment-entry not defined");
            }

            log.debug("Orphaned : amortization-entry item with the Id : {} dated : {}", prepaymentEntryId, prepaymentEntryDate);

            return foundReconciliation.getId();
        }
        findByIdAndDate = found.getId();
        amortizationEntryDTO.setOrphaned(false);
        return findByIdAndDate;
    }
}
