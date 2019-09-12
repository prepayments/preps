package io.github.prepayments.app.resources;

import io.github.prepayments.app.models.AmortizationScheduleDTO;
import io.github.prepayments.app.models.BalanceQuery;
import io.github.prepayments.app.services.AmortizationScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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

    private final AmortizationScheduleService<BalanceQuery> balanceQueryAmortizationScheduleService;

    public AmortizationScheduleResource(final AmortizationScheduleService<BalanceQuery> balanceQueryAmortizationScheduleService) {
        this.balanceQueryAmortizationScheduleService = balanceQueryAmortizationScheduleService;
    }

    /**
     * {@code GET /amortization-schedule}: Generate an amortization schedule
     *
     * @param httpServletRequest
     * @return
     */
    @GetMapping("/amortization-schedule")
    public ResponseEntity<List<AmortizationScheduleDTO>> getAmortizationSchedule(HttpServletRequest httpServletRequest) {
        BalanceQuery balanceQuery = BalanceQuery.builder()
                                                .balanceDate(LocalDate.parse(httpServletRequest.getParameter("balanceDate"),DATETIME_FORMATTER))
                                                .serviceOutlet(httpServletRequest.getParameter("serviceOutlet"))
                                                .accountName(httpServletRequest.getParameter("accountName"))
                                                .build();
        List<AmortizationScheduleDTO> scheduleAmortization = balanceQueryAmortizationScheduleService.scheduleAmortization(balanceQuery);
        return ResponseEntity.ok().body(scheduleAmortization);
    }
}
