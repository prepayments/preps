package io.github.prepayments.app.util;

import com.google.common.collect.ImmutableList;
import io.github.prepayments.app.messaging.data_entry.vm.SimpleAmortizationUploadEVM;
import io.github.prepayments.service.dto.AmortizationUploadDTO;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.math.RoundingMode.HALF_EVEN;

@Component("amortizationUploadEVMDTOMapper")
public class DefaultAmortizationUploadEVMDTOMapper implements AmortizationUploadEVMDTOMapper {

    @Override
    public SimpleAmortizationUploadEVM toExcelView(final AmortizationUploadDTO amortizationUploadDTO, final DateTimeFormatter dtf) {

        // TODO implement this !!

        return null;
    }

    @Override
    public AmortizationUploadDTO toDto(final SimpleAmortizationUploadEVM simpleAmortizationUploadEVM, final DateTimeFormatter dtf) {

        // @formatter:off
        return AmortizationUploadDTO.builder()
                                                         .accountName(simpleAmortizationUploadEVM.getAccountName())
                                                         .particulars(simpleAmortizationUploadEVM.getParticulars())
                                                         .amortizationServiceOutletCode(simpleAmortizationUploadEVM.getAmortizationServiceOutletCode())
                                                         .prepaymentServiceOutletCode(simpleAmortizationUploadEVM.getPrepaymentServiceOutletCode())
                                                         .prepaymentAccountNumber(simpleAmortizationUploadEVM.getPrepaymentAccountNumber())
                                                         .expenseAccountNumber(simpleAmortizationUploadEVM.getExpenseAccountNumber())
                                                         .prepaymentTransactionId(simpleAmortizationUploadEVM.getPrepaymentTransactionId())
                                                         .prepaymentTransactionDate(LocalDate.parse(simpleAmortizationUploadEVM.getPrepaymentTransactionDate(), dtf))
                                                         .prepaymentTransactionAmount(
                                                             NumberUtils.toScaledBigDecimal(simpleAmortizationUploadEVM.getPrepaymentTransactionAmount().replace(",",""),2, HALF_EVEN))
                                                         .amortizationAmount(
                                                             NumberUtils.toScaledBigDecimal(simpleAmortizationUploadEVM.getAmortizationAmount().replace(",",""),2, HALF_EVEN))
                                                         .numberOfAmortizations(NumberUtils.toInt(simpleAmortizationUploadEVM.getNumberOfAmortizations().replace(",","")))
                                                         .firstAmortizationDate(LocalDate.parse(simpleAmortizationUploadEVM.getFirstAmortizationDate(), dtf))
                                                         .OriginatingFileToken(simpleAmortizationUploadEVM.getOriginatingFileToken())
                                                       .build();
        // @formatter:on

    }

    @Override
    public List<SimpleAmortizationUploadEVM> toExcelView(final List<AmortizationUploadDTO> amortizationUploadDTOS, final DateTimeFormatter dtf) {
        return amortizationUploadDTOS.stream().map(dto -> this.toExcelView(dto, dtf)).collect(ImmutableList.toImmutableList());
    }

    @Override
    public List<AmortizationUploadDTO> toDto(final List<SimpleAmortizationUploadEVM> simpleAmortizationUploadEVMS, final DateTimeFormatter dtf) {
        return simpleAmortizationUploadEVMS.stream().map(evm -> this.toDto(evm, dtf)).collect(ImmutableList.toImmutableList());
    }
}
