package io.github.prepayments.app.resources;

import io.github.prepayments.app.models.BalanceQuery;
import io.github.prepayments.app.models.PrepaymentTimeBalanceDTO;
import io.github.prepayments.app.services.reports.ShouldGetBalance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
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

    private final ShouldGetBalance<BalanceQuery, PrepaymentTimeBalanceDTO, BigDecimal> prepaymentBalanceQueryService;

    public TimeBalanceResource(final ShouldGetBalance<BalanceQuery, PrepaymentTimeBalanceDTO, BigDecimal> prepaymentBalanceQueryService) {
        this.prepaymentBalanceQueryService = prepaymentBalanceQueryService;
    }

    // @formatter:off
    /**
     * POST  /prepayments : get all the balances for prepayment by particular date.
     *
     * It's weird I know but I do need to wrap the request in an object which is further used to create
     * listing based on items criteria specified in the object fields. This will enable in future to expand or reduce
     * the parameters required for this request to run.
     * This unfortunately gives the impression that we are saving something. If therefore the client does
     * not observe for the response, you are screwed. So watch out!
     * Basically the client on angular might induce the httpClient from the angular container like so:
     *
     * getEntities(
     *     balanceQuery: IBalanceQuery = new BalanceQuery({
     *       balanceDate: moment('2019-01-01', 'YYYY-MM-DD'),
     *       accountName: 'PREPAYMENT ACCOUNT',
     *       serviceOutlet: '100'
     *     })): Observable<EntityArrayResponseType> {
     *
     *     return this.http
     *       .post<IPrepaymentTimeBalance[]>(this.resourceUrl, balanceQuery, { observe: 'response' })
     *       .pipe(
     *         tap((res: EntityArrayResponseType) => this.log.info(`fetched : ${res.body.length} prepayment balance items`)),
     *         catchError(this.handleError<IPrepaymentTimeBalance[]>('getEntities', []))
     *       ).pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
     *   }
     *
     *
     * Carefully note the observe response option in the get parameter
     *
     * @return the ResponseEntity with status 200 (OK) and the list of prepayment-balances in body
     */
    @PostMapping("/prepayments")
    public ResponseEntity<List<PrepaymentTimeBalanceDTO>> getPrepaymentBalance(@Valid @RequestBody BalanceQuery balanceQuery) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        log.debug("REST request to get all prepayment-balances for the balances query: {}", balanceQuery);
        List<PrepaymentTimeBalanceDTO> prepaymentBalances = prepaymentBalanceQueryService.getBalance(balanceQuery);
        return ResponseEntity.ok().body(prepaymentBalances);
    }
    // @formatter:on

    @PostMapping("/prepayments/amount")
    public ResponseEntity<BigDecimal> getPrepaymentBalanceAmount(@Valid @RequestBody BalanceQuery balanceQuery) {
        log.debug("REST request to get all prepayment-balances' total amount for the balances query: {}", balanceQuery);
        BigDecimal prepaymentBalances = prepaymentBalanceQueryService.getBalanceAmount(balanceQuery);
        return ResponseEntity.ok().body(prepaymentBalances);
    }
}
