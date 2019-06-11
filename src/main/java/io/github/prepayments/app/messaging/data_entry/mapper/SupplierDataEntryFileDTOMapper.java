package io.github.prepayments.app.messaging.data_entry.mapper;

import io.github.prepayments.app.messaging.DtoMapper;
import io.github.prepayments.app.messaging.data_entry.vm.RegisteredSupplierEVM;
import io.github.prepayments.service.dto.RegisteredSupplierDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface SupplierDataEntryFileDTOMapper extends DtoMapper<RegisteredSupplierEVM, RegisteredSupplierDTO> {

    default RegisteredSupplierDTO fromId(Long id) {
        if (id == null) {
            return null;
        }
        RegisteredSupplierDTO supplierDataEntryFileDTO = new RegisteredSupplierDTO();
        supplierDataEntryFileDTO.setId(id);
        return supplierDataEntryFileDTO;
    }
}
