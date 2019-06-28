package io.github.prepayments.app.messaging.data_entry.listener;

import io.github.prepayments.app.messaging.data_entry.vm.SimpleAmortizationUploadEVM;
import io.github.prepayments.app.messaging.filing.streams.FilingAmortizationUploadStreams;
import io.github.prepayments.app.messaging.filing.vm.AmortizationEntryEVM;
import io.github.prepayments.app.messaging.services.AmortizationDataEntryMessageService;
import io.github.prepayments.service.AmortizationUploadService;
import io.github.prepayments.service.dto.AmortizationUploadDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;

@Slf4j
@Component
public class DataSinkAmortizationUploadsStreamsListener {

    private final AmortizationUploadService amortizationUploadService;
    private final AmortizationDataEntryMessageService amortizationDataEntryMessageService;

    public DataSinkAmortizationUploadsStreamsListener(final AmortizationUploadService amortizationUploadService, final AmortizationDataEntryMessageService amortizationDataEntryMessageService) {
        this.amortizationUploadService = amortizationUploadService;
        this.amortizationDataEntryMessageService = amortizationDataEntryMessageService;
    }

    @StreamListener(FilingAmortizationUploadStreams.INPUT)
    public void handleAmortizationUploadStreams(@Payload SimpleAmortizationUploadEVM simpleAmortizationUploadEVM) {
        log.info("Received amortization upload #: {} standby for persistence...", simpleAmortizationUploadEVM.getRowIndex());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        // @formatter:off
        AmortizationUploadDTO dto = AmortizationUploadDTO.builder()
                                                         .accountName(simpleAmortizationUploadEVM.getAccountName())
                                                         .particulars(simpleAmortizationUploadEVM.getParticulars())
                                                         .serviceOutletCode(simpleAmortizationUploadEVM.getServiceOutlet())
                                                         .prepaymentAccountNumber(simpleAmortizationUploadEVM.getPrepaymentAccountNumber())
                                                         .expenseAccountNumber(simpleAmortizationUploadEVM.getExpenseAccountNumber())
                                                         .prepaymentTransactionId(simpleAmortizationUploadEVM.getPrepaymentTransactionId())
                                                         .prepaymentTransactionDate(LocalDate.parse(simpleAmortizationUploadEVM.getPrepaymentTransactionDate(), dtf))
                                                         .prepaymentTransactionAmount(
                                                             NumberUtils.toScaledBigDecimal(simpleAmortizationUploadEVM.getPrepaymentTransactionAmount().replace(",",""),2, RoundingMode.HALF_EVEN))
                                                         .amortizationAmount(
                                                             NumberUtils.toScaledBigDecimal(simpleAmortizationUploadEVM.getAmortizationAmount().replace(",",""),2, RoundingMode.HALF_EVEN))
                                                         .numberOfAmortizations(NumberUtils.toInt(simpleAmortizationUploadEVM.getNumberOfAmortizations().replace(",","")))
                                                         .firstAmortizationDate(LocalDate.parse(simpleAmortizationUploadEVM.getPrepaymentTransactionDate(), dtf))
                                                       .build();
        // @formatter:on

        AmortizationUploadDTO result = amortizationUploadService.save(dto);

        propagateAmortizationEntries(simpleAmortizationUploadEVM, dtf, result);

        log.debug("Amortization upload item saved : #{}", result.getId());
    }

    private void propagateAmortizationEntries(final @Payload SimpleAmortizationUploadEVM simpleAmortizationUploadEVM, final AmortizationUploadDTO result) {
        propagateAmortizationEntries(simpleAmortizationUploadEVM, DateTimeFormatter.ofPattern("yyyy/MM/dd"), result);
    }

    private void propagateAmortizationEntries(final @Payload SimpleAmortizationUploadEVM simpleAmortizationUploadEVM, final DateTimeFormatter dtf, final AmortizationUploadDTO result) {
        IntStream.rangeClosed(0, result.getNumberOfAmortizations()-1).forEach((sequence) -> {

            String amortizationDateInstance = incrementDate(simpleAmortizationUploadEVM.getFirstAmortizationDate(), sequence, dtf);

            log.debug("Sending for persistence the amortization instance for the date: {}", amortizationDateInstance);

            amortizationDataEntryMessageService.sendMessage(
                AmortizationEntryEVM.builder()
                                    .amortizationDate(amortizationDateInstance)
                                    .amortizationAmount(
                                        NumberUtils.toScaledBigDecimal(
                                            simpleAmortizationUploadEVM.getAmortizationAmount()
                                                                       .replace(",",""), 2, RoundingMode.HALF_EVEN).toPlainString())
                                    .particulars(result.getParticulars())
                                    .serviceOutlet(result.getServiceOutletCode())
                                    .accountNumber(result.getExpenseAccountNumber())
                                    .accountName(result.getAccountName())
                                    .prepaymentEntryId(result.getPrepaymentTransactionId())
                                    .prepaymentEntryDate(simpleAmortizationUploadEVM.getPrepaymentTransactionDate())
                                    .build());
        });
    }

    private String incrementDate(final String amortizationDate, final int sequence, final DateTimeFormatter dtf) {
        return dtf.format(LocalDate.parse(amortizationDate, dtf).plusMonths(sequence));
    }
}
