package io.github.prepayments.app.messaging.data_entry.mapper;

import io.github.prepayments.app.messaging.DtoMapper;
import io.github.prepayments.app.messaging.data_entry.vm.SimplePrepaymentEntryEVM;
import io.github.prepayments.service.dto.PrepaymentEntryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {})
public interface PrepaymentDataEntryFileDTOMapper extends DtoMapper<SimplePrepaymentEntryEVM, PrepaymentEntryDTO> {

    default PrepaymentEntryDTO fromId(Long id) {
        if (id == null) {
            return null;
        }
        PrepaymentEntryDTO prepaymentEntryDTO = new PrepaymentEntryDTO();
        prepaymentEntryDTO.setId(id);
        return prepaymentEntryDTO;
    }

    @Mappings( {@Mapping(target = "prepaymentDate", dateFormat = "yyyy/MM/dd"),})
    @Override
    PrepaymentEntryDTO toDTO(SimplePrepaymentEntryEVM simplePrepaymentEntryEVM);

    @Mappings( {@Mapping(target = "prepaymentDate", dateFormat = "yyyy/MM/dd"),})
    @Override
    SimplePrepaymentEntryEVM toEVM(PrepaymentEntryDTO prepaymentEntryDTO);
}
