package io.github.prepayments.app.services.reports;

import io.github.prepayments.app.models.PrepaymentTimeBalanceDTO;
import io.github.prepayments.app.services.AmortizationEntryReportService;
import io.github.prepayments.service.PrepaymentEntryService;
import io.github.prepayments.service.dto.AmortizationEntryDTO;
import io.github.prepayments.service.dto.PrepaymentEntryDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

@Service("onCallAmortizationService")
public class OnCallAmortizationService {

    private final PrepaymentEntryService prepaymentEntryService;

    public OnCallAmortizationService(final PrepaymentEntryService prepaymentEntryService) {
        this.prepaymentEntryService = prepaymentEntryService;
    }

    public Stream<PrepaymentTimeBalanceDTO> amortize(final LocalDate upTo, final PrepaymentEntryDTO prepaymentEntryDTO, final AmortizationEntryReportService amortizationEntryReportService) {

        BigDecimal amortizationAmount = amortizationEntryReportService.findAllByPrepaymentId(prepaymentEntryService.findOne(prepaymentEntryDTO.getId()).get())
                                                                      .stream()
                                                                      .filter(amort -> amort.getAmortizationDate().isBefore(upTo))
                                                                      .map(AmortizationEntryDTO::getAmortizationAmount)
                                                                      .reduce((amort, amount) -> amount.add(amort))
                                                                      .orElse(BigDecimal.ZERO);

        return Stream.of(PrepaymentTimeBalanceDTO.builder()
                                                 .id(prepaymentEntryDTO.getId())
                                                 .accountNumber(prepaymentEntryDTO.getAccountNumber())
                                                 .accountName(prepaymentEntryDTO.getAccountName())
                                                 .prepaymentId(prepaymentEntryDTO.getPrepaymentId())
                                                 .prepaymentDate(prepaymentEntryDTO.getPrepaymentDate())
                                                 .particulars(prepaymentEntryDTO.getParticulars())
                                                 .serviceOutlet(prepaymentEntryDTO.getServiceOutlet())
                                                 .prepaymentAmount(prepaymentEntryDTO.getPrepaymentAmount())
                                                 .months(prepaymentEntryDTO.getMonths())
                                                 .supplierName(prepaymentEntryDTO.getSupplierName())
                                                 .invoiceNumber(prepaymentEntryDTO.getInvoiceNumber())
                                                 .scannedDocumentId(prepaymentEntryDTO.getScannedDocumentId())
                                                 .balanceAmount(prepaymentEntryDTO.getPrepaymentAmount().subtract(amortizationAmount))
                                                 .build());
    }
}
