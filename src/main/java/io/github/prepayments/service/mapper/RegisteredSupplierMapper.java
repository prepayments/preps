package io.github.prepayments.service.mapper;

import io.github.prepayments.domain.RegisteredSupplier;
import io.github.prepayments.service.dto.RegisteredSupplierDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link RegisteredSupplier} and its DTO {@link RegisteredSupplierDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RegisteredSupplierMapper extends EntityMapper<RegisteredSupplierDTO, RegisteredSupplier> {


    default RegisteredSupplier fromId(Long id) {
        if (id == null) {
            return null;
        }
        RegisteredSupplier registeredSupplier = new RegisteredSupplier();
        registeredSupplier.setId(id);
        return registeredSupplier;
    }
}
