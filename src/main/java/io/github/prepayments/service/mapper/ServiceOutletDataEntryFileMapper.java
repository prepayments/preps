package io.github.prepayments.service.mapper;

import io.github.prepayments.domain.*;
import io.github.prepayments.service.dto.ServiceOutletDataEntryFileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceOutletDataEntryFile} and its DTO {@link ServiceOutletDataEntryFileDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ServiceOutletDataEntryFileMapper extends EntityMapper<ServiceOutletDataEntryFileDTO, ServiceOutletDataEntryFile> {



    default ServiceOutletDataEntryFile fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceOutletDataEntryFile serviceOutletDataEntryFile = new ServiceOutletDataEntryFile();
        serviceOutletDataEntryFile.setId(id);
        return serviceOutletDataEntryFile;
    }
}
