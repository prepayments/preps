package io.github.prepayments.app.services.reports;

import io.github.prepayments.app.models.PrepaymentTimeBalanceDTO;
import io.github.prepayments.app.services.AmortizationEntryReportService;
import io.github.prepayments.app.services.PrepaymentEntryReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Transactional
@Service("prepaymentTimeBalanceService")
public class PrepaymentTimeBalanceService implements ShouldGetBalance<String, PrepaymentTimeBalanceDTO> {
// TODO public class PrepaymentTimeBalanceService implements ShouldGetBalance<BalanceQuery, PrepaymentTimeBalanceDTO> {

    private final PrepaymentEntryReportService prepaymentEntryReportService;
    private final AmortizationEntryReportService amortizationEntryReportService;
    private final OnCallAmortizationService onCallAmortizationService;

    public PrepaymentTimeBalanceService(final PrepaymentEntryReportService prepaymentEntryReportService, final AmortizationEntryReportService amortizationEntryReportService,
                                        final OnCallAmortizationService onCallAmortizationService) {
        this.prepaymentEntryReportService = prepaymentEntryReportService;
        this.amortizationEntryReportService = amortizationEntryReportService;
        this.onCallAmortizationService = onCallAmortizationService;
    }

    @Override
    public List<PrepaymentTimeBalanceDTO> getBalance(final String balanceDate) {
    // TODO Public List<PrepaymentTimeBalanceDTO> getBalance(final BalanceQuery balanceQuery) {

        log.info("Request for prepayment balances for the date: {}", balanceDate);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // @formatter:off
        return prepaymentEntryReportService.findAll()
                                           .stream()
                                           .filter(prep -> prep.getPrepaymentDate().isBefore(LocalDate.parse(balanceDate, dtf)))
                                           .map(prep -> onCallAmortizationService.amortize(LocalDate.parse(balanceDate, dtf), prep, amortizationEntryReportService))
                                           .map(prep -> prep.findAny().get())
                                           .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::<PrepaymentTimeBalanceDTO>unmodifiableList));
        // @formatter:on
    }
}
