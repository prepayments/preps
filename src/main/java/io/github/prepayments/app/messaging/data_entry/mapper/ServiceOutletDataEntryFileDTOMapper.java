package io.github.prepayments.app.messaging.data_entry.mapper;

import io.github.prepayments.app.messaging.DtoMapper;
import io.github.prepayments.app.messaging.data_entry.vm.SimpleServiceOutletEVM;
import io.github.prepayments.service.dto.ServiceOutletDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ServiceOutletDataEntryFileDTOMapper extends DtoMapper<SimpleServiceOutletEVM, ServiceOutletDTO> {

    default ServiceOutletDTO fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceOutletDTO serviceOutletDTO = new ServiceOutletDTO();
        serviceOutletDTO.setId(id);
        return serviceOutletDTO;
    }


}
