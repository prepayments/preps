package io.github.prepayments.app.messaging.data_entry.service;

import io.github.prepayments.app.messaging.filing.vm.AmortizationEntryEVM;
import io.github.prepayments.app.messaging.services.AmortizationDataEntryMessageService;
import io.github.prepayments.service.dto.AmortizationUploadDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;

@Slf4j
@Transactional
@Service("amortizationEntriesPropagatorService")
public class FunctionalSequenceAmortizationEntriesPropagator implements AmortizationEntriesPropagatorService {

    private final AmortizationDataEntryMessageService amortizationDataEntryMessageService;

    public FunctionalSequenceAmortizationEntriesPropagator(final AmortizationDataEntryMessageService amortizationDataEntryMessageService) {
        this.amortizationDataEntryMessageService = amortizationDataEntryMessageService;
    }

    /**
     * Propagates the upload DTO into constituent amortization entries Default DateTimeFormatter is used : DateTimeFormatter.ofPattern("yyyy/MM/dd")
     *
     * @param amortizationUploadDTO The upload to be propagated into constituent amortization-entries
     */
    @Override
    public void propagateAmortizationEntries(final AmortizationUploadDTO amortizationUploadDTO) {
        propagateAmortizationEntries(DateTimeFormatter.ofPattern("yyyy/MM/dd"), amortizationUploadDTO);
    }

    /**
     * Propagates the upload DTO into constituent amortization entries
     *
     * @param dtf                   DateTimeFormatter for converting date items back to formatted string
     * @param amortizationUploadDTO The upload to be propagated into constituent amortization-entries
     */
    @Override
    public void propagateAmortizationEntries(final DateTimeFormatter dtf, final AmortizationUploadDTO amortizationUploadDTO) {

        IntStream.rangeClosed(0, amortizationUploadDTO.getNumberOfAmortizations() - 1).forEach((sequence) -> {

            String amortizationDateInstance = incrementDate(amortizationUploadDTO.getFirstAmortizationDate().format(dtf), sequence, dtf);

            log.debug("Sending for persistence the amortization instance for the date: {}", amortizationDateInstance);

            amortizationDataEntryMessageService.sendMessage(AmortizationEntryEVM.builder()
                                                                                .amortizationDate(amortizationDateInstance)
                                                                                .amortizationAmount(NumberUtils.toScaledBigDecimal(amortizationUploadDTO.getAmortizationAmount().toPlainString(), 2,
                                                                                                                                   RoundingMode.HALF_EVEN).toPlainString())
                                                                                .particulars(amortizationUploadDTO.getParticulars())
                                                                                .serviceOutlet(amortizationUploadDTO.getServiceOutletCode())
                                                                                .accountNumber(amortizationUploadDTO.getExpenseAccountNumber())
                                                                                .accountName(amortizationUploadDTO.getAccountName())
                                                                                .prepaymentEntryId(amortizationUploadDTO.getPrepaymentTransactionId())
                                                                                .prepaymentEntryDate(amortizationUploadDTO.getPrepaymentTransactionDate().format(dtf))
                                                                                .build());
        });
    }

    private String incrementDate(final String amortizationDate, final int sequence, final DateTimeFormatter dtf) {
        return dtf.format(LocalDate.parse(amortizationDate, dtf).plusMonths(sequence));
    }
}
