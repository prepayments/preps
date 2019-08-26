package io.github.prepayments.app.util;

import com.google.common.collect.ImmutableList;
import io.github.prepayments.app.messaging.data_entry.service.IPrepaymentEntryIdService;
import io.github.prepayments.app.messaging.data_entry.vm.SimpleAmortizationEntryEVM;
import io.github.prepayments.service.dto.AmortizationEntryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.apache.commons.lang3.math.NumberUtils.createBigDecimal;

// TODO change to simpleAmortizationEntryEVMToDTOMapper
@Slf4j
@Component("amortizationEntryEVMDTOMapper")
public class DefaultAmortizationEntryEVMDTOMapper implements SimpleAmortizationEntryEVMDTOMapper {

    private final IPrepaymentEntryIdService prepaymentEntryIdService;

    public DefaultAmortizationEntryEVMDTOMapper(final IPrepaymentEntryIdService prepaymentEntryIdService) {
        this.prepaymentEntryIdService = prepaymentEntryIdService;
    }

    @Override
    public SimpleAmortizationEntryEVM toExcelView(final AmortizationEntryDTO amortizationEntryDTO, final DateTimeFormatter dtf) {

        return SimpleAmortizationEntryEVM.builder()
                                         .amortizationDate(dtf.format(amortizationEntryDTO.getAmortizationDate()))
                                         .amortizationAmount(amortizationEntryDTO.getAmortizationAmount().toPlainString())
                                         .particulars(amortizationEntryDTO.getParticulars())
                                         .prepaymentServiceOutlet(amortizationEntryDTO.getPrepaymentServiceOutlet())
                                         .prepaymentAccountNumber(amortizationEntryDTO.getPrepaymentAccountNumber())
                                         .amortizationServiceOutlet(amortizationEntryDTO.getAmortizationServiceOutlet())
                                         .amortizationAccountNumber(amortizationEntryDTO.getAmortizationAccountNumber())
                                         .originatingFileToken(amortizationEntryDTO.getOriginatingFileToken())
                                         .amortizationTag(amortizationEntryDTO.getAmortizationTag())
                                         .orphaned(amortizationEntryDTO.getOrphaned())
                                         .build();
    }

    @Override
    public AmortizationEntryDTO toDto(final SimpleAmortizationEntryEVM excelView, DateTimeFormatter dtf) {

        AmortizationEntryDTO dto = null;
        try {
            dto = AmortizationEntryDTO.builder()
                                      .amortizationDate(LocalDate.parse(excelView.getAmortizationDate(), dtf))
                                      // remove commas just in case someone forgot to remove formatting from the excel file
                                      .amortizationAmount(createBigDecimal(excelView.getAmortizationAmount().replace(",", "")))
                                      .particulars(excelView.getParticulars())
                                      .prepaymentServiceOutlet(excelView.getPrepaymentServiceOutlet())
                                      .prepaymentAccountNumber(excelView.getPrepaymentAccountNumber())
                                      .amortizationServiceOutlet(excelView.getAmortizationServiceOutlet())
                                      .amortizationAccountNumber(excelView.getAmortizationAccountNumber())
                                      .originatingFileToken(excelView.getOriginatingFileToken())
                                      .amortizationTag(excelView.getAmortizationTag())
                                      .orphaned(excelView.getOrphaned())
                                      .build();

        } catch (NumberFormatException e) {
            // TODO invoke front-end notification service with file-id, or file-token on this
            log.error("NumberFormatException encountered : Kindly check if the excel file amortization-amount column has been formatted with comma-style", e);
        }

        // Purists may consider this anti-pattern but if an error occurs here I would rather nuke the whole row
        if (dto != null) {
            dto.setPrepaymentEntryId(prepaymentEntryIdService.findByIdAndDate(dto, excelView.getPrepaymentEntryId(), excelView.getPrepaymentEntryDate()));
        }

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
