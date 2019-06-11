package io.github.prepayments.app.messaging.filing.mapper;

import io.github.prepayments.app.messaging.DtoMapper;
import io.github.prepayments.app.messaging.filing.vm.AmortizationEntryEVM;
import io.github.prepayments.service.dto.AmortizationEntryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface AmortizationDataFilingDTOMapper extends DtoMapper<AmortizationEntryEVM, AmortizationEntryDTO> {


    default AmortizationEntryDTO fromId(Long id) {
        if (id == null) {
            return null;
        }
        AmortizationEntryDTO amortizationEntryDTO = new AmortizationEntryDTO();
        amortizationEntryDTO.setId(id);
        return amortizationEntryDTO;
    }
}
