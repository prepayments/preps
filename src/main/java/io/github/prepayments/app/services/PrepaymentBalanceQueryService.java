package io.github.prepayments.app.services;

import com.google.common.collect.ImmutableList;
import io.github.prepayments.app.models.BalanceQuery;
import io.github.prepayments.app.models.PrepaymentTimeBalanceDTO;
import io.github.prepayments.app.services.criteria.QueryParameterCriteriaService;
import io.github.prepayments.app.services.reports.OnCallAmortizationService;
import io.github.prepayments.app.services.reports.ShouldGetBalance;
import io.github.prepayments.service.PrepaymentEntryQueryService;
import io.github.prepayments.service.dto.PrepaymentEntryCriteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static io.github.prepayments.app.AppConstants.DATETIME_FORMATTER;

/**
 * This object implements the should-get-balance interface same as the prepayment-time-balance-service however that is where the similarity ends. The latter pulls an entire data set from repository
 * and filters the data using stream api. This class however aims to filter the data source from the get go using query api, inorder to enable more dynamism, especially because we do not want to
 * dictate to the client what filters can be applie as query parameters, all that being embodied the balance-query object
 */
@Transactional
@Service("prepaymentBalanceQueryService")
public class PrepaymentBalanceQueryService implements ShouldGetBalance<BalanceQuery, PrepaymentTimeBalanceDTO, BigDecimal> {

    private final PrepaymentEntryQueryService prepaymentEntryQueryService;
    private final OnCallAmortizationService onCallAmortizationService;
    private final AmortizationEntryReportService amortizationEntryReportService;
    private final QueryParameterCriteriaService<BalanceQuery, PrepaymentEntryCriteria> balanceQueryPrepaymentEntryCriteria;

    public PrepaymentBalanceQueryService(final PrepaymentEntryQueryService prepaymentEntryQueryService, final OnCallAmortizationService onCallAmortizationService,
                                         final AmortizationEntryReportService amortizationEntryReportService,
                                         final QueryParameterCriteriaService<BalanceQuery, PrepaymentEntryCriteria> balanceQueryPrepaymentEntryCriteria) {
        this.prepaymentEntryQueryService = prepaymentEntryQueryService;
        this.onCallAmortizationService = onCallAmortizationService;
        this.amortizationEntryReportService = amortizationEntryReportService;
        this.balanceQueryPrepaymentEntryCriteria = balanceQueryPrepaymentEntryCriteria;
    }

    /**
     * Returns an amount of all outstanding balances of type A having received a request of type R
     */
    @Override
    public BigDecimal getBalanceAmount(final BalanceQuery requestParameter) {
        // @formatter:off
        return this.getBalance(requestParameter)
                   .stream()
                   .map(PrepaymentTimeBalanceDTO::getBalanceAmount)
                   .reduce(BigDecimal::add)
                   .orElse(BigDecimal.ZERO);
        // @formatter:on
    }

    /**
     * Return true if the prepayment balance is more than zero
     *
     * @param prep
     * @return
     */
    private static boolean nonZeroValueTest(PrepaymentTimeBalanceDTO prep) {
        return prep.getBalanceAmount().compareTo(BigDecimal.TEN) > 0 || prep.getBalanceAmount().compareTo(BigDecimal.ONE.negate()) < 0;
    }

    @Override
    public List<PrepaymentTimeBalanceDTO> getBalance(final BalanceQuery requestParameter) {

        PrepaymentEntryCriteria prepaymentEntryCriteria = balanceQueryPrepaymentEntryCriteria.getCriteria(requestParameter);

        // @formatter:off
        return prepaymentEntryQueryService.findByCriteria(prepaymentEntryCriteria)
                                           .stream()
                                           .flatMap(prep -> onCallAmortizationService.amortize(LocalDate.parse(requestParameter.getBalanceDate().format(DATETIME_FORMATTER), DATETIME_FORMATTER), prep,
                                                                                           amortizationEntryReportService))
                                           .filter(PrepaymentBalanceQueryService::nonZeroValueTest)
                                           .collect(ImmutableList.toImmutableList());
        // @formatter:on
    }

    /*private PrepaymentEntryCriteria getPrepaymentEntryCriteria(final BalanceQuery requestParameter) {
        PrepaymentEntryCriteria prepaymentEntryCriteria = new PrepaymentEntryCriteria();

        // TODO create query criteria
        LocalDateFilter balanceDate = new LocalDateFilter();
        balanceDate.setLessOrEqualThan(requestParameter.getBalanceDate());
        StringFilter serviceOutletCode = new StringFilter();
        serviceOutletCode.setEquals(requestParameter.getServiceOutlet());
        StringFilter prepaymentAccountName = new StringFilter();
        prepaymentAccountName.setContains(requestParameter.getAccountName());

        // TODO update criteria for data account and service-outlet-code
        prepaymentEntryCriteria.setPrepaymentDate(balanceDate);
        if(!requestParameter.getServiceOutlet().equalsIgnoreCase(GENERAL_QUERY_REQUEST_ARGS)) {
            prepaymentEntryCriteria.setServiceOutlet(serviceOutletCode);
        }
        if(!requestParameter.getAccountName().equalsIgnoreCase(GENERAL_QUERY_REQUEST_ARGS)) {
            prepaymentEntryCriteria.setAccountName(prepaymentAccountName);
        }
        return prepaymentEntryCriteria;
    }*/
}
