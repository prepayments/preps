package io.github.prepayments.app.services;

/**
 * This interface updates the criteria using parameters and data from a type of source.
 * IF we implement an object to update the amortizationCriteria using data from the amortization
 * upload we would do somthing like:
 *
 * AmortizationEntryCriteria amortizationEntryCriteria = new AmortizationEntryCriteria();
 *
 * amortizationUploadAmortizationEntryCriteriaUpdate.updateCriteria(cascadable, amortizationEntryCriteria);
 *
 * List<AmortizationEntryDTO> amortizationEntries = amortizationEntryQueryService.findByCriteria(amortizationEntryCriteria);
 *
 * @param <Source> Type of source from which filter data is obtained
 * @param <Criteria> Type of criteria updated by the source data
 */
public interface CriteriaUpdate<Source, Criteria> {

    void updateCriteria(Source source, Criteria criteria);
}
