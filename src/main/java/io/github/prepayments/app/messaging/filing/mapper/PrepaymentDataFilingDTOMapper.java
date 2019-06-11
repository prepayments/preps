package io.github.prepayments.app.messaging.filing.mapper;

import io.github.prepayments.app.messaging.DtoMapper;
import io.github.prepayments.app.messaging.filing.vm.PrepaymentEntryEVM;
import io.github.prepayments.service.dto.PrepaymentEntryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface PrepaymentDataFilingDTOMapper extends DtoMapper<PrepaymentEntryEVM, PrepaymentEntryDTO> {

    default PrepaymentEntryDTO fromId(Long id) {
        if (id == null) {
            return null;
        }
        PrepaymentEntryDTO prepaymentEntryDTO = new PrepaymentEntryDTO();
        prepaymentEntryDTO.setId(id);
        return prepaymentEntryDTO;
    }
}
