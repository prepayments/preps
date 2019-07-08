package io.github.prepayments.app.services.reports;

import io.github.prepayments.app.models.PrepaymentTimeBalanceDTO;
import io.github.prepayments.app.services.AmortizationEntryReportService;
import io.github.prepayments.app.services.PrepaymentEntryReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Transactional
@Service("prepaymentTimeBalanceService")
public class PrepaymentTimeBalanceService implements ShouldGetBalance<String, PrepaymentTimeBalanceDTO> {

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

        log.info("Request for prepayment balances for the date: {}", balanceDate);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<PrepaymentTimeBalanceDTO> prepayments = new ArrayList<>();

        prepaymentEntryReportService.findAll().stream().filter(prep -> {
            return prep.getPrepaymentDate().isBefore(LocalDate.parse(balanceDate, dtf));
        }).map(prep -> {
            return onCallAmortizationService.amortize(LocalDate.parse(balanceDate, dtf), prep, amortizationEntryReportService);
        }).forEach(prep -> {
            prepayments.add(prep.findAny().get());
        });

        // TODO Collectors.collectingAndThen(Collectors.toList(), Collections::<PrepaymentTimeBalanceDTO>unmodifiableList)
        return prepayments;
    }
}
