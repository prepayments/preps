package io.github.prepayments.app.util;

import org.springframework.stereotype.Component;
import io.github.prepayments.service.dto.AmortizationUploadDTO;
import io.github.prepayments.app.messaging.filing.vm.AmortizationEntryEVM;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

import static io.github.prepayments.app.AppConstants.DATETIME_FORMAT;
import static java.math.RoundingMode.HALF_EVEN;
import static org.apache.commons.lang3.math.NumberUtils.toScaledBigDecimal;

@Component("amortizationUploadAmortizationEntryEVMMapper")
public class AmortizationUploadAmortizationEntryEVMMapper {

    public AmortizationEntryEVM toAmortizationEntry(final AmortizationUploadDTO amortizationUploadDTO, final String amortizationDateInstance) {
        return toAmortizationEntry(amortizationUploadDTO, amortizationDateInstance, DateTimeFormatter.ofPattern(DATETIME_FORMAT));
    }

    public AmortizationEntryEVM toAmortizationEntry(final AmortizationUploadDTO amortizationUploadDTO, final String amortizationDateInstance, final DateTimeFormatter dtf) {

        // @formatter:off
        return  AmortizationEntryEVM.builder()
                                       .amortizationDate(amortizationDateInstance)
                                       .amortizationAmount(toScaledBigDecimal(amortizationUploadDTO.getAmortizationAmount().toPlainString(), 2, HALF_EVEN).toPlainString())
                                       .particulars(amortizationUploadDTO.getParticulars())
                                       .prepaymentEntryId(amortizationUploadDTO.getPrepaymentTransactionId())
                                       .prepaymentEntryDate(amortizationUploadDTO.getPrepaymentTransactionDate().format(dtf))
                                       .prepaymentServiceOutlet(amortizationUploadDTO.getPrepaymentServiceOutletCode())
                                       .prepaymentAccountNumber(amortizationUploadDTO.getPrepaymentAccountNumber())
                                       .amortizationServiceOutlet(amortizationUploadDTO.getAmortizationServiceOutletCode())
                                       .amortizationAccountNumber(amortizationUploadDTO.getExpenseAccountNumber())
                                       .originatingFileToken(amortizationUploadDTO.getOriginatingFileToken())
                                       .build();
        // @formatter:on
    }
}
