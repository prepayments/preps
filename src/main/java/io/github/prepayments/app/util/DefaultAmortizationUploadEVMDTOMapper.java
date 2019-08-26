package io.github.prepayments.app.util;

import com.google.common.collect.ImmutableList;
import io.github.prepayments.app.messaging.data_entry.vm.SimpleAmortizationUploadEVM;
import io.github.prepayments.service.dto.AmortizationUploadDTO;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.math.RoundingMode.HALF_EVEN;
import static java.time.LocalDate.parse;
import static org.apache.commons.lang3.math.NumberUtils.toScaledBigDecimal;

@Component("amortizationUploadEVMDTOMapper")
public class DefaultAmortizationUploadEVMDTOMapper implements SimpleAmortizationUploadEVMDTOMapper {

    @Override
    public SimpleAmortizationUploadEVM toExcelView(final AmortizationUploadDTO amortizationUploadDTO, final DateTimeFormatter dtf) {

        return SimpleAmortizationUploadEVM.builder()
                                          .accountName(amortizationUploadDTO.getAccountName())
                                          .particulars(amortizationUploadDTO.getParticulars())
                                          .amortizationServiceOutletCode(amortizationUploadDTO.getAmortizationServiceOutletCode())
                                          .prepaymentServiceOutletCode(amortizationUploadDTO.getPrepaymentServiceOutletCode())
                                          .prepaymentAccountNumber(amortizationUploadDTO.getPrepaymentAccountNumber())
                                          .expenseAccountNumber(amortizationUploadDTO.getExpenseAccountNumber())
                                          .prepaymentTransactionId(amortizationUploadDTO.getPrepaymentTransactionId())
                                          .prepaymentTransactionDate(dtf.format(amortizationUploadDTO.getPrepaymentTransactionDate()))
                                          .prepaymentTransactionAmount(amortizationUploadDTO.getPrepaymentTransactionAmount().toPlainString())
                                          .amortizationAmount(amortizationUploadDTO.getAmortizationAmount().toPlainString())
                                          .numberOfAmortizations(String.valueOf(amortizationUploadDTO.getNumberOfAmortizations()))
                                          .firstAmortizationDate(dtf.format(amortizationUploadDTO.getFirstAmortizationDate()))
                                          .originatingFileToken(amortizationUploadDTO.getOriginatingFileToken())
                                          .amortizationTag(amortizationUploadDTO.getAmortizationTag())
                                          .uploadSuccessful(amortizationUploadDTO.getUploadSuccessful())
                                          .uploadOrphaned(amortizationUploadDTO.getUploadOrphaned())
                                          .build();
    }

    @Override
    public AmortizationUploadDTO toDto(final SimpleAmortizationUploadEVM simpleAmortizationUploadEVM, final DateTimeFormatter dtf) {

        return AmortizationUploadDTO.builder()
                                    .accountName(simpleAmortizationUploadEVM.getAccountName())
                                    .particulars(simpleAmortizationUploadEVM.getParticulars())
                                    .amortizationServiceOutletCode(simpleAmortizationUploadEVM.getAmortizationServiceOutletCode())
                                    .prepaymentServiceOutletCode(simpleAmortizationUploadEVM.getPrepaymentServiceOutletCode())
                                    .prepaymentAccountNumber(simpleAmortizationUploadEVM.getPrepaymentAccountNumber())
                                    .expenseAccountNumber(simpleAmortizationUploadEVM.getExpenseAccountNumber())
                                    .prepaymentTransactionId(simpleAmortizationUploadEVM.getPrepaymentTransactionId())
                                    .prepaymentTransactionDate(parse(simpleAmortizationUploadEVM.getPrepaymentTransactionDate(), dtf))
                                    .prepaymentTransactionAmount(toScaledBigDecimal(simpleAmortizationUploadEVM.getPrepaymentTransactionAmount().replace(",", ""), 2, HALF_EVEN))
                                    .amortizationAmount(toScaledBigDecimal(simpleAmortizationUploadEVM.getAmortizationAmount().replace(",", ""), 2, HALF_EVEN))
                                    .numberOfAmortizations(NumberUtils.toInt(simpleAmortizationUploadEVM.getNumberOfAmortizations().replace(",", "")))
                                    .firstAmortizationDate(parse(simpleAmortizationUploadEVM.getFirstAmortizationDate(), dtf))
                                    .originatingFileToken(simpleAmortizationUploadEVM.getOriginatingFileToken())
                                    .amortizationTag(simpleAmortizationUploadEVM.getAmortizationTag())
                                    .uploadSuccessful(simpleAmortizationUploadEVM.getUploadSuccessful())
                                    .uploadOrphaned(simpleAmortizationUploadEVM.getUploadOrphaned())
                                    .build();

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
