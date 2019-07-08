package io.github.prepayments.app.resources;

import io.github.prepayments.app.models.PrepaymentTimeBalanceDTO;
import io.github.prepayments.app.services.reports.ShouldGetBalance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * Provides the balance for an account or transaction or entry based on given date for a request
 * and the account opening date of transaction posting date
 *
 */
@Slf4j
@RestController
@RequestMapping("/api/reports/balances")
public class TimeBalanceResource {

    private final ShouldGetBalance<String, PrepaymentTimeBalanceDTO> prepaymentTimeBalanceService;

    public TimeBalanceResource(final ShouldGetBalance<String, PrepaymentTimeBalanceDTO> prepaymentTimeBalanceService) {
        this.prepaymentTimeBalanceService = prepaymentTimeBalanceService;
    }

    /**
     * GET  /prepayment-balances-list : get all the balances for prepayment by particular date.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of prepayment-balances in body
     */
    @GetMapping("/prepayments")
    public ResponseEntity<List<PrepaymentTimeBalanceDTO>> getPrepaymentBalance(@RequestParam String balanceDate) {
        log.debug("REST request to get all prepayment-balances as at the date: {}", balanceDate);
        List<PrepaymentTimeBalanceDTO> prepaymentBalances = prepaymentTimeBalanceService.getBalance(balanceDate);
        //        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/amortization-entries");
        return ResponseEntity.ok().body(prepaymentBalances);
    }
}
