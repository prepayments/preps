package io.github.prepayments.app.services.criteria;

import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.prepayments.app.models.BalanceQuery;
import io.github.prepayments.service.dto.PrepaymentEntryCriteria;
import org.springframework.stereotype.Service;

import static io.github.prepayments.app.AppConstants.GENERAL_QUERY_REQUEST_ARGS;

@Service("balanceQueryPrepaymentEntryCriteria")
public class BalanceQueryPrepaymentEntryCriteria implements QueryParameterCriteriaService<BalanceQuery, PrepaymentEntryCriteria> {


    /**
     * Gets criteria of type C given query parameters of type Q
     */
    @Override
    public PrepaymentEntryCriteria getCriteria(final BalanceQuery queryParameter) {
        PrepaymentEntryCriteria prepaymentEntryCriteria = new PrepaymentEntryCriteria();

        // TODO create query criteria
        LocalDateFilter balanceDate = new LocalDateFilter();
        balanceDate.setLessOrEqualThan(queryParameter.getBalanceDate());
        StringFilter serviceOutletCode = new StringFilter();
        serviceOutletCode.setEquals(queryParameter.getServiceOutlet());
        StringFilter prepaymentAccountName = new StringFilter();
        prepaymentAccountName.setContains(queryParameter.getAccountName());

        // TODO update criteria for data account and service-outlet-code
        prepaymentEntryCriteria.setPrepaymentDate(balanceDate);
        if(!queryParameter.getServiceOutlet().equalsIgnoreCase(GENERAL_QUERY_REQUEST_ARGS)) {
            prepaymentEntryCriteria.setServiceOutlet(serviceOutletCode);
        }
        if(!queryParameter.getAccountName().equalsIgnoreCase(GENERAL_QUERY_REQUEST_ARGS)) {
            prepaymentEntryCriteria.setAccountName(prepaymentAccountName);
        }
        return prepaymentEntryCriteria;
    }
}
