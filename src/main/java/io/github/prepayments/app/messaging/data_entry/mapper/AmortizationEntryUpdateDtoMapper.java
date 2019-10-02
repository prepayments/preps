package io.github.prepayments.app.messaging.data_entry.mapper;

import io.github.prepayments.app.messaging.DtoMapper;
import io.github.prepayments.app.messaging.data_entry.vm.SimpleAmortizationEntryUpdateEVM;
import io.github.prepayments.service.dto.AmortizationEntryUpdateDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static io.github.prepayments.app.AppConstants.DATETIME_FORMATTER;
import static org.apache.commons.lang3.math.NumberUtils.createBigDecimal;

@Component
public class AmortizationEntryUpdateDtoMapper implements DtoMapper<SimpleAmortizationEntryUpdateEVM, AmortizationEntryUpdateDTO> {

    @Override
    public AmortizationEntryUpdateDTO toDTO(final SimpleAmortizationEntryUpdateEVM evm) {

        return AmortizationEntryUpdateDTO.builder()
                                         .amortizationEntryId(Long.parseLong(evm.getAmortizationEntryId()))
                                         .amortizationDate(LocalDate.parse(evm.getAmortizationDate(), DATETIME_FORMATTER))
                                         .amortizationAmount(createBigDecimal(evm.getAmortizationAmount()))
                                         .particulars(evm.getParticulars())
                                         .prepaymentServiceOutlet(evm.getPrepaymentServiceOutlet())
                                         .prepaymentAccountNumber(evm.getPrepaymentAccountNumber())
                                         .amortizationServiceOutlet(evm.getAmortizationServiceOutlet())
                                         .amortizationAccountNumber(evm.getAmortizationAccountNumber())
                                         .prepaymentEntryId(Long.parseLong(evm.getPrepaymentEntryId()))
                                         .originatingFileToken(evm.getOriginatingFileToken())
                                         .amortizationTag(evm.getAmortizationTag())
                                         .orphaned(evm.orphaned())
                                         .build();
    }

    @Override
    public SimpleAmortizationEntryUpdateEVM toEVM(final AmortizationEntryUpdateDTO dto) {

        return SimpleAmortizationEntryUpdateEVM.builder()
                                               .amortizationEntryId(dto.getAmortizationEntryId().toString())
                                               .amortizationDate(DATETIME_FORMATTER.format(dto.getAmortizationDate()))
                                               .amortizationAmount(dto.getAmortizationAmount().toPlainString())
                                               .particulars(dto.getParticulars())
                                               .prepaymentServiceOutlet(dto.getPrepaymentServiceOutlet())
                                               .prepaymentAccountNumber(dto.getPrepaymentAccountNumber())
                                               .amortizationServiceOutlet(dto.getAmortizationServiceOutlet())
                                               .amortizationAccountNumber(dto.getAmortizationAccountNumber())
                                               .prepaymentEntryId(dto.getPrepaymentEntryId().toString())
                                               .originatingFileToken(dto.getOriginatingFileToken())
                                               .amortizationTag(dto.getAmortizationTag())
                                               .orphaned(dto.getOrphaned())
                                               .build();
    }
}
