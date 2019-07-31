package io.github.prepayments.app.util;

import com.google.common.collect.ImmutableList;
import io.github.prepayments.app.messaging.data_entry.service.IPrepaymentEntryIdService;
import io.github.prepayments.app.messaging.data_entry.vm.SimpleAmortizationEntryEVM;
import io.github.prepayments.service.dto.AmortizationEntryDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static io.github.prepayments.app.AppConstants.DATETIME_FORMAT;

@Component("amortizationEntryEVMDTOMapper")
public class DefaultAmortizationEntryEVMDTOMapper implements AmortizationEntryEVMDTOMapper {

    private final IPrepaymentEntryIdService prepaymentEntryIdService;

    public DefaultAmortizationEntryEVMDTOMapper(final IPrepaymentEntryIdService prepaymentEntryIdService) {
        this.prepaymentEntryIdService = prepaymentEntryIdService;
    }

    @Override
    public SimpleAmortizationEntryEVM toExcelView(final AmortizationEntryDTO amortizationEntryDTO, final DateTimeFormatter dtf) {

        // TODO Implement this!!!

        return null;
    }

    @Override
    public AmortizationEntryDTO toDto(final SimpleAmortizationEntryEVM excelView, DateTimeFormatter dtf) {

        // TODO DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATETIME_FORMAT);

        // @formatter:off
        AmortizationEntryDTO dto = AmortizationEntryDTO.builder()
                                                       .amortizationDate(LocalDate.parse(excelView.getAmortizationDate(), dtf))
                                                       .amortizationAmount(BigDecimal.valueOf(Double.parseDouble(excelView.getAmortizationAmount())))
                                                       .particulars(excelView.getParticulars())
                                                       .prepaymentServiceOutlet(excelView.getPrepaymentServiceOutlet())
                                                       .prepaymentAccountNumber(excelView.getPrepaymentAccountNumber())
                                                       .amortizationServiceOutlet(excelView.getAmortizationServiceOutlet())
                                                       .amortizationAccountNumber(excelView.getAmortizationAccountNumber())
                                                       .OriginatingFileToken(excelView.getOriginatingFileToken())
                                                       .build();
        // @formatter:on

        // @formatter:off
        dto.setPrepaymentEntryId(
            prepaymentEntryIdService.findByIdAndDate(
                excelView.getPrepaymentEntryId(),
                excelView.getPrepaymentEntryDate()));
        // @formatter:on

        return dto;
    }

    @Override
    public List<SimpleAmortizationEntryEVM> toExcelView(final List<AmortizationEntryDTO> entryDTOS, final DateTimeFormatter dtf) {
        return entryDTOS.stream().map(dto -> this.toExcelView(dto, dtf)).collect(ImmutableList.toImmutableList());
    }

    @Override
    public List<AmortizationEntryDTO> toDto(final List<SimpleAmortizationEntryEVM> excelViews, final DateTimeFormatter dtf) {
        return excelViews.stream().map(evm -> this.toDto(evm, dtf)).collect(ImmutableList.toImmutableList());
    }
}
