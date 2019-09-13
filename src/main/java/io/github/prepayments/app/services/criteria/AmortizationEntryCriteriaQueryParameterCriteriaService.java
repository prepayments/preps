package io.github.prepayments.app.services.criteria;

import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.prepayments.app.models.BalanceQuery;
import io.github.prepayments.service.TransactionAccountQueryService;
import io.github.prepayments.service.dto.AmortizationEntryCriteria;
import io.github.prepayments.service.dto.TransactionAccountCriteria;
import org.springframework.stereotype.Component;

import static io.github.prepayments.app.AppConstants.GENERAL_QUERY_REQUEST_ARGS;
import static io.github.prepayments.app.AppConstants.MONTHLY_AMORTIZATION_DATE;

/**
 * This criteria service has one expensive operation that is supposed to find an account
 * number given the account name from the query. Now that comes with an assumption that the
 * transaction-account table is populated. If however that table is not populated, you need to look elsewhere.
 *
 */
@Component("amortizationEntryCriteriaQueryParameterCriteriaService")
public class AmortizationEntryCriteriaQueryParameterCriteriaService implements QueryParameterCriteriaService<BalanceQuery, AmortizationEntryCriteria> {

    private final TransactionAccountQueryService transactionAccountQueryService;

    public AmortizationEntryCriteriaQueryParameterCriteriaService(final TransactionAccountQueryService transactionAccountQueryService) {
        this.transactionAccountQueryService = transactionAccountQueryService;
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

        if(!queryParameter.getAccountName().equalsIgnoreCase(GENERAL_QUERY_REQUEST_ARGS)) {
            // TODO Create account account criteria
            TransactionAccountCriteria accountCriteria = transactionAccountCriteria(queryParameter.getAccountName());
            StringFilter prepaymentAccountNumber = new StringFilter();
            prepaymentAccountNumber.setEquals(transactionAccountQueryService.findByCriteria(accountCriteria).get(0).getAccountNumber());
            amortizationEntryCriteria.setPrepaymentAccountNumber(prepaymentAccountNumber);
        }

        // TODO update criteria for data account and service-outlet-code
        amortizationEntryCriteria.setAmortizationDate(balanceDate);
        if(!queryParameter.getServiceOutlet().equalsIgnoreCase(GENERAL_QUERY_REQUEST_ARGS)) {
            amortizationEntryCriteria.setAmortizationServiceOutlet(serviceOutletCode);
        }

        return amortizationEntryCriteria;
    }

    private TransactionAccountCriteria transactionAccountCriteria(final String accountName) {
        TransactionAccountCriteria transactionAccountCriteria = new TransactionAccountCriteria();
        StringFilter accountNameFilter = new StringFilter();
        accountNameFilter.setContains(accountName);
        transactionAccountCriteria.setAccountName(accountNameFilter);
        return transactionAccountCriteria;
    }
}
