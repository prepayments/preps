package io.github.prepayments.app.resources;

import io.github.prepayments.app.models.AmortizationScheduleDTO;
import io.github.prepayments.app.models.BalanceQuery;
import io.github.prepayments.app.services.AmortizationScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static io.github.prepayments.app.AppConstants.DATETIME_FORMATTER;

/**
 * This resource responds to queries on scheduling of amortization entries
 *
 */
@RestController
@RequestMapping("/api/data")
public class AmortizationScheduleResource {

    private final AmortizationScheduleService<BalanceQuery, BigDecimal> balanceQueryAmortizationScheduleService;

    public AmortizationScheduleResource(final AmortizationScheduleService<BalanceQuery, BigDecimal> balanceQueryAmortizationScheduleService) {
        this.balanceQueryAmortizationScheduleService = balanceQueryAmortizationScheduleService;
    }

    /**
     * {@code GET /amortization-schedule}: Generate an amortization schedule
     *
     * @param balanceQuery
     * @return
     */
    @PostMapping("/amortization-schedule")
    public ResponseEntity<List<AmortizationScheduleDTO>> getAmortizationSchedule(@Valid @RequestBody BalanceQuery balanceQuery) {
        List<AmortizationScheduleDTO> scheduleAmortization = balanceQueryAmortizationScheduleService.scheduleAmortization(balanceQuery);
        return ResponseEntity.ok().body(scheduleAmortization);
    }

    /**
     * {@code GET /amortization-schedule/amount}: Generate amortization schedule amount as per query params
     *
     * @param balanceQuery
     * @return
     */
    @PostMapping("/amortization-schedule/amount")
    public ResponseEntity<BigDecimal> getAmortizationScheduleAmount(@Valid @RequestBody BalanceQuery balanceQuery) {
        BigDecimal scheduleAmortizationAmount = balanceQueryAmortizationScheduleService.scheduleAmortizationAmount(balanceQuery);
        return ResponseEntity.ok().body(scheduleAmortizationAmount);
    }
}
