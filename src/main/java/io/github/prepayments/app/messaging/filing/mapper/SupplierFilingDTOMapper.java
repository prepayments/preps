package io.github.prepayments.app.messaging.filing.mapper;

import io.github.prepayments.app.messaging.DtoMapper;
import io.github.prepayments.app.messaging.filing.vm.RegisteredSupplierEVM;
import io.github.prepayments.service.dto.SupplierDataEntryFileDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface SupplierFilingDTOMapper extends DtoMapper<RegisteredSupplierEVM, SupplierDataEntryFileDTO> {

    default SupplierDataEntryFileDTO fromId(Long id) {
        if (id == null) {
            return null;
        }
        SupplierDataEntryFileDTO supplierDataEntryFileDTO = new SupplierDataEntryFileDTO();
        supplierDataEntryFileDTO.setId(id);
        return supplierDataEntryFileDTO;
    }
}
