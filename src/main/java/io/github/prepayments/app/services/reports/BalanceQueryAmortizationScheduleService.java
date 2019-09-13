package io.github.prepayments.app.services.reports;

import com.google.common.collect.ImmutableList;
import io.github.prepayments.app.GeneralMapper;
import io.github.prepayments.app.models.AmortizationScheduleDTO;
import io.github.prepayments.app.models.BalanceQuery;
import io.github.prepayments.app.services.AmortizationScheduleService;
import io.github.prepayments.app.services.QueryParameterCriteriaService;
import io.github.prepayments.service.AmortizationEntryQueryService;
import io.github.prepayments.service.dto.AmortizationEntryCriteria;
import io.github.prepayments.service.dto.AmortizationEntryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("balanceQueryAmortizationScheduleService")
public class BalanceQueryAmortizationScheduleService implements AmortizationScheduleService<BalanceQuery> {

    private final QueryParameterCriteriaService<BalanceQuery, AmortizationEntryCriteria> amortizationEntryCriteriaQueryParameterCriteriaService;
    private final AmortizationEntryQueryService amortizationEntryQueryService;
    private final GeneralMapper<AmortizationEntryDTO, AmortizationScheduleDTO> amortizationScheduleDTOGeneralMapper;
    private final PrepaymentBalanceService<AmortizationScheduleDTO> amortizationSchedulePrepaymentBalanceService;

    public BalanceQueryAmortizationScheduleService(final QueryParameterCriteriaService<BalanceQuery, AmortizationEntryCriteria> amortizationEntryCriteriaQueryParameterCriteriaService,
                                                   final AmortizationEntryQueryService amortizationEntryQueryService,
                                                   final GeneralMapper<AmortizationEntryDTO, AmortizationScheduleDTO> amortizationScheduleDTOGeneralMapper,
                                                   final PrepaymentBalanceService<AmortizationScheduleDTO> amortizationSchedulePrepaymentBalanceService) {
        this.amortizationEntryCriteriaQueryParameterCriteriaService = amortizationEntryCriteriaQueryParameterCriteriaService;
        this.amortizationEntryQueryService = amortizationEntryQueryService;
        this.amortizationScheduleDTOGeneralMapper = amortizationScheduleDTOGeneralMapper;
        this.amortizationSchedulePrepaymentBalanceService = amortizationSchedulePrepaymentBalanceService;
    }

    @Override
    public List<AmortizationScheduleDTO> scheduleAmortization(final BalanceQuery queryParameters) {

        AmortizationEntryCriteria amortizationEntryCriteria = amortizationEntryCriteriaQueryParameterCriteriaService.getCriteria(queryParameters);

        List<AmortizationEntryDTO> amortizationEntries = amortizationEntryQueryService.findByCriteria(amortizationEntryCriteria);

        return amortizationScheduleDTOGeneralMapper.toTypeT2(amortizationEntries).stream()
            .peek(entry -> {
                entry.setPrepaymentBalance(amortizationSchedulePrepaymentBalanceService.getBalance(entry));
            })
            .collect(ImmutableList.toImmutableList());
    }
}