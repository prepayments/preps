package io.github.prepayments.app.messaging.data_entry.service;

import io.github.prepayments.app.messaging.filing.vm.AmortizationEntryEVM;
import io.github.prepayments.app.messaging.services.AmortizationDataEntryMessageService;
import io.github.prepayments.app.util.AmortizationUploadAmortizationEntryEVMMapper;
import io.github.prepayments.service.dto.AmortizationUploadDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FunctionalSequenceAmortizationEntriesPropagatorTest {

    private AmortizationEntriesPropagatorService amortizationEntriesPropagatorService;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private static final int MONTHLY_AMORTIZATION_DATE = 20;

    @BeforeEach
    void setUp() {
        AmortizationDataEntryMessageService amortizationDataEntryMessageService = Mockito.mock(AmortizationDataEntryMessageService.class);
        amortizationEntriesPropagatorService =
            new FunctionalSequenceAmortizationEntriesPropagator(
                amortizationDataEntryMessageService, new AmortizationUploadAmortizationEntryEVMMapper());
    }

    @Test
    void propagateAmortizationEntries() {

        AmortizationUploadDTO dto = AmortizationUploadDTO.builder()
                             .accountName("Prepayment-X")
                             .particulars("Prepayment of X")
                             .amortizationServiceOutletCode("980")
                             .prepaymentServiceOutletCode("980")
                             .prepaymentAccountNumber("4546562")
                             .expenseAccountNumber("4546563")
                             .prepaymentTransactionId("TX100")
                             .prepaymentTransactionDate(LocalDate.of(2019, 6, 10))
                             .prepaymentTransactionAmount(BigDecimal.TEN)
                             .amortizationAmount(BigDecimal.ONE)
                             .numberOfAmortizations(10)
                             .firstAmortizationDate(LocalDate.of(2019, 6, 20))
                             .build();
        // @formatter:off
        List<AmortizationEntryEVM> evms =
            amortizationEntriesPropagatorService.propagateAmortizationEntries(DATE_TIME_FORMATTER, dto, MONTHLY_AMORTIZATION_DATE);
        // @formatter:on

        assertEquals(LocalDate.of(2019, 6, 20).format(DATE_TIME_FORMATTER), evms.get(0).getAmortizationDate());
        assertEquals(LocalDate.of(2019, 7, 20).format(DATE_TIME_FORMATTER), evms.get(1).getAmortizationDate());
        assertEquals(LocalDate.of(2019, 8, 20).format(DATE_TIME_FORMATTER), evms.get(2).getAmortizationDate());
        assertEquals(LocalDate.of(2019, 9, 20).format(DATE_TIME_FORMATTER), evms.get(3).getAmortizationDate());
        assertEquals(LocalDate.of(2019, 10, 20).format(DATE_TIME_FORMATTER), evms.get(4).getAmortizationDate());
        assertEquals(LocalDate.of(2019, 11, 20).format(DATE_TIME_FORMATTER), evms.get(5).getAmortizationDate());
        assertEquals(LocalDate.of(2020, 3, 20).format(DATE_TIME_FORMATTER), evms.get(9).getAmortizationDate());
        assertEquals(10, evms.size());
    }
}
