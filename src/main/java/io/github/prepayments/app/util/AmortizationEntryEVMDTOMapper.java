package io.github.prepayments.app.util;

import io.github.prepayments.app.messaging.data_entry.vm.SimpleAmortizationEntryEVM;
import io.github.prepayments.service.dto.AmortizationEntryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface AmortizationEntryEVMDTOMapper extends ExcelViewMapper<SimpleAmortizationEntryEVM, AmortizationEntryDTO> {
}
