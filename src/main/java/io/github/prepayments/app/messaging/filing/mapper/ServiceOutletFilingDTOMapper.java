package io.github.prepayments.app.messaging.filing.mapper;

import io.github.prepayments.app.messaging.DtoMapper;
import io.github.prepayments.app.messaging.filing.vm.ServiceOutletEVM;
import io.github.prepayments.service.dto.ServiceOutletDataEntryFileDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ServiceOutletFilingDTOMapper extends DtoMapper<ServiceOutletEVM, ServiceOutletDataEntryFileDTO> {

    default ServiceOutletDataEntryFileDTO fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceOutletDataEntryFileDTO serviceOutletDataEntryFileDTO = new ServiceOutletDataEntryFileDTO();
        serviceOutletDataEntryFileDTO.setId(id);
        return serviceOutletDataEntryFileDTO;
    }
}
