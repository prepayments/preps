package io.github.prepayments.app.services.criteria;

import io.github.prepayments.app.models.BalanceQuery;
import io.github.prepayments.app.services.QueryParameterCriteriaService;
import io.github.prepayments.service.dto.AmortizationEntryCriteria;
import org.springframework.stereotype.Component;

@Component("amortizationEntryCriteriaQueryParameterCriteriaService")
public class AmortizationEntryCriteriaQueryParameterCriteriaService implements QueryParameterCriteriaService<BalanceQuery, AmortizationEntryCriteria> {

    /**
     * Gets criteria of type C given query parameters of type Q
     */
    @Override
    public AmortizationEntryCriteria getCriteria(final BalanceQuery queryParameter) {
        return null;
    }
}
