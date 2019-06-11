package io.github.prepayments.app.resources;

import io.github.prepayments.app.services.AmortizationEntryReportService;
import io.github.prepayments.app.services.PrepaymentEntryReportService;
import io.github.prepayments.app.services.RegisteredSupplierReportService;
import io.github.prepayments.app.services.ServiceOutletReportService;
import io.github.prepayments.app.services.TransactionAccountsReportService;
import io.github.prepayments.service.dto.AmortizationEntryDTO;
import io.github.prepayments.service.dto.PrepaymentEntryDTO;
import io.github.prepayments.service.dto.RegisteredSupplierDTO;
import io.github.prepayments.service.dto.ServiceOutletDTO;
import io.github.prepayments.service.dto.TransactionAccountDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/reports")
public class AllListReportResource {

    private final AmortizationEntryReportService amortizationEntryReportService;
    private final PrepaymentEntryReportService prepaymentEntryReportService;
    private final RegisteredSupplierReportService registeredSupplierReportService;
    private final ServiceOutletReportService serviceOutletReportService;
    private final TransactionAccountsReportService transactionAccountsReportService;

    public AllListReportResource(final AmortizationEntryReportService amortizationEntryReportService, final PrepaymentEntryReportService prepaymentEntryReportService,
                                 final RegisteredSupplierReportService registeredSupplierReportService, final ServiceOutletReportService serviceOutletReportService,
                                 final TransactionAccountsReportService transactionAccountsReportService) {
        this.amortizationEntryReportService = amortizationEntryReportService;
        this.prepaymentEntryReportService = prepaymentEntryReportService;
        this.registeredSupplierReportService = registeredSupplierReportService;
        this.serviceOutletReportService = serviceOutletReportService;
        this.transactionAccountsReportService = transactionAccountsReportService;
    }

    /**
     * GET  /amortization-entries-list : get all the amortizationEntries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of amortizationEntries in body
     */
    @GetMapping("/amortization-entries-list")
    public ResponseEntity<List<AmortizationEntryDTO>> getAllAmortizationEntries() {
        log.debug("REST request to get all AmortizationEntries");
        List<AmortizationEntryDTO> amortizationEntries = amortizationEntryReportService.findAll();
        //        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/amortization-entries");
        return ResponseEntity.ok().body(amortizationEntries);
    }

    /**
     * GET  /prepayment-entries-list : get all the prepaymentEntries
     *
     * @return the ResponseEntity with status 200 (OK) and the list of prepaymentEntries in body
     */
    @GetMapping("/prepayment-entries-list")
    public ResponseEntity<List<PrepaymentEntryDTO>> getAllPrepaymentEntries() {
        log.debug("REST request to get all PrepaymentEntries");
        List<PrepaymentEntryDTO> dtoList = prepaymentEntryReportService.findAll();
        return ResponseEntity.ok().body(dtoList);
    }

    /**
     * GET  /registered-suppliers-list : get all the registered suppliers
     *
     * @return the ResponseEntity with status 200 (OK) and the list of registered suppliers in body
     */
    @GetMapping("/registered-suppliers-list")
    public ResponseEntity<List<RegisteredSupplierDTO>> getAllRegisteredSuppliers() {
        log.debug("REST request to get all registered suppliers");
        List<RegisteredSupplierDTO> dtoList = registeredSupplierReportService.findAll();
        return ResponseEntity.ok().body(dtoList);
    }

    /**
     * GET  /service-outlets-list : get all the service outlets
     *
     * @return the ResponseEntity with status 200 (OK) and the list of registered suppliers in body
     */
    @GetMapping("/service-outlets-list")
    public ResponseEntity<List<ServiceOutletDTO>> getAllServiceOutlets() {
        log.debug("REST request to get all service outlets");
        List<ServiceOutletDTO> dtoList = serviceOutletReportService.findAll();
        return ResponseEntity.ok().body(dtoList);
    }

    /**
     * GET  /transaction-accounts-list : get all the transaction accounts
     *
     * @return the ResponseEntity with status 200 (OK) and the list of registered suppliers in body
     */
    @GetMapping("/transaction-accounts-list")
    public ResponseEntity<List<TransactionAccountDTO>> getAllTransactionAccounts() {
        log.debug("REST request to get all transaction accounts");
        List<TransactionAccountDTO> dtoList = transactionAccountsReportService.findAll();
        return ResponseEntity.ok().body(dtoList);
    }
}
