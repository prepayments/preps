package io.github.prepayments.app.services;

import io.github.prepayments.app.models.BalanceQuery;
import io.github.prepayments.service.dto.PrepaymentEntryCriteria;

/**
 * This interface allows clients to obtain criteria for filtering backend
 * data base on the query-parameters
 */
public interface QueryParameterCriteriaService<Q, C> {

    /**
     * Gets criteria of type C given query parameters of type Q
     */
    C getCriteria(Q queryParameter);
}
