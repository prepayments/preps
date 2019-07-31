package io.github.prepayments.app.messaging.data_entry.service;

import io.github.prepayments.app.messaging.filing.vm.AmortizationEntryEVM;
import io.github.prepayments.app.messaging.services.AmortizationDataEntryMessageService;
import io.github.prepayments.app.util.AmortizationUploadAmortizationEntryEVMMapper;
import io.github.prepayments.service.dto.AmortizationUploadDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static io.github.prepayments.app.AppConstants.DATETIME_FORMAT;

/**
 * This important class breaks up  a single amortization-upload entity into its constituent amortization-entry entities due for each period beginning from the first month of amortization
 * <p>
 * TODO Add this implementation into the amortization-upload-resource-post method to propagate entries on normal postings
 */
@Slf4j
@Transactional
@Service("amortizationEntriesPropagatorService")
public class FunctionalSequenceAmortizationEntriesPropagator implements AmortizationEntriesPropagatorService {

    private final AmortizationDataEntryMessageService amortizationDataEntryMessageService;
    private final AmortizationUploadAmortizationEntryEVMMapper uploadAmortizationEntryEVMMapper;

    public FunctionalSequenceAmortizationEntriesPropagator(AmortizationDataEntryMessageService amortizationDataEntryMessageService,
                                                           @Qualifier("amortizationUploadAmortizationEntryEVMMapper") AmortizationUploadAmortizationEntryEVMMapper uploadAmortizationEntryEVMMapper) {
        this.amortizationDataEntryMessageService = amortizationDataEntryMessageService;
        this.uploadAmortizationEntryEVMMapper = uploadAmortizationEntryEVMMapper;
    }

    /**
     * Propagates the upload DTO into constituent amortization entries Default DateTimeFormatter is used : DateTimeFormatter.ofPattern("yyyy/MM/dd")
     *
     * @param amortizationUploadDTO   The upload to be propagated into constituent amortization-entries
     * @param monthlyAmortizationDate Day of the month when amortization of prepayments is carried out...
     */
    @Override
    public List<AmortizationEntryEVM> propagateAmortizationEntries(final AmortizationUploadDTO amortizationUploadDTO, final int monthlyAmortizationDate) {
        return propagateAmortizationEntries(DateTimeFormatter.ofPattern(DATETIME_FORMAT), amortizationUploadDTO, monthlyAmortizationDate);
    }

    /**
     * Propagates the upload DTO into constituent amortization entries
     *
     * @param dtf                     DateTimeFormatter for converting date items back to formatted string
     * @param amortizationUploadDTO   The upload to be propagated into constituent amortization-entries
     * @param monthlyAmortizationDate Day of the month when amortization of prepayments is carried out...
     */
    @Override
    public List<AmortizationEntryEVM> propagateAmortizationEntries(final DateTimeFormatter dtf, final AmortizationUploadDTO amortizationUploadDTO, final int monthlyAmortizationDate) {

        List<AmortizationEntryEVM> evms = new ArrayList<>();

        IntStream.rangeClosed(0, amortizationUploadDTO.getNumberOfAmortizations() - 1).forEach((sequence) -> {

            String amortizationDateInstance = incrementDate(amortizationUploadDTO.getFirstAmortizationDate(), sequence, dtf, monthlyAmortizationDate);

            log.debug("Sending for persistence the amortization instance for the date: {}", amortizationDateInstance);

            AmortizationEntryEVM evm = uploadAmortizationEntryEVMMapper.toAmortizationEntry(amortizationUploadDTO, amortizationDateInstance);

            amortizationDataEntryMessageService.sendMessage(evm);

            evms.add(evm);
        });

        return evms;
    }

    private String incrementDate(final LocalDate firstAmortizationDate, final int sequence, final DateTimeFormatter dtf, final int monthlyAmortizationDate) {
        return dtf.format(firstAmortizationDate.plusMonths(sequence).with(TemporalAdjusters.firstDayOfMonth()).plusDays(monthlyAmortizationDate - 1));
    }
}
