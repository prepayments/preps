package io.github.prepayments.app.services.criteria;

import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.prepayments.app.models.BalanceQuery;
import io.github.prepayments.service.PrepaymentEntryQueryService;
import io.github.prepayments.service.dto.AmortizationEntryCriteria;
import io.github.prepayments.service.dto.PrepaymentEntryCriteria;
import org.springframework.stereotype.Component;

import static io.github.prepayments.app.AppConstants.GENERAL_QUERY_REQUEST_ARGS;
import static io.github.prepayments.app.AppConstants.MONTHLY_AMORTIZATION_DATE;

@Component("prepaymentEntryQueryParameterCriteriaService")
public class PrepaymentEntryQueryParameterCriteriaService implements QueryParameterCriteriaService<BalanceQuery, AmortizationEntryCriteria> {

    private final PrepaymentEntryQueryService prepaymentEntryQueryService;

    public PrepaymentEntryQueryParameterCriteriaService(final PrepaymentEntryQueryService prepaymentEntryQueryService) {
        this.prepaymentEntryQueryService = prepaymentEntryQueryService;
    }

    /**
     * Gets criteria of type C given query parameters of type Q
     */
    @Override
    public AmortizationEntryCriteria getCriteria(final BalanceQuery queryParameter) {

        AmortizationEntryCriteria amortizationEntryCriteria = new AmortizationEntryCriteria();

        LocalDateFilter balanceDate = new LocalDateFilter();
        balanceDate.setEquals(queryParameter.getBalanceDate().withDayOfMonth(MONTHLY_AMORTIZATION_DATE));
        StringFilter serviceOutletCode = new StringFilter();
        serviceOutletCode.setEquals(queryParameter.getServiceOutlet());
        StringFilter prepaymentAccountName = new StringFilter();
        prepaymentAccountName.setContains(queryParameter.getAccountName());

        if (!queryParameter.getAccountName().equalsIgnoreCase(GENERAL_QUERY_REQUEST_ARGS)) {
            // TODO Create account account criteria
            PrepaymentEntryCriteria accountCriteria = transactionAccountCriteria(queryParameter.getAccountName());

            StringFilter prepaymentAccountNumber = new StringFilter();
            prepaymentAccountNumber.setEquals(prepaymentEntryQueryService.findByCriteria(accountCriteria).get(0).getAccountNumber());
            amortizationEntryCriteria.setPrepaymentAccountNumber(prepaymentAccountNumber);
        }

        // TODO update criteria for data account and service-outlet-code
        amortizationEntryCriteria.setAmortizationDate(balanceDate);
        if (!queryParameter.getServiceOutlet().equalsIgnoreCase(GENERAL_QUERY_REQUEST_ARGS)) {
            amortizationEntryCriteria.setAmortizationServiceOutlet(serviceOutletCode);
        }

        return amortizationEntryCriteria;
    }

    /**
     * We have an account name, now what we need is an account number
     */
    private PrepaymentEntryCriteria transactionAccountCriteria(final String prepaymentAccountName) {
        PrepaymentEntryCriteria transactionAccountCriteria = new PrepaymentEntryCriteria();
        StringFilter prepaymentAccountNameFilter = new StringFilter();
        prepaymentAccountNameFilter.setContains(prepaymentAccountName);
        transactionAccountCriteria.setAccountName(prepaymentAccountNameFilter);
        return transactionAccountCriteria;
    }

}
