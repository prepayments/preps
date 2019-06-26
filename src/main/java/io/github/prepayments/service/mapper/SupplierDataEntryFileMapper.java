package io.github.prepayments.service.mapper;

import io.github.prepayments.domain.SupplierDataEntryFile;
import io.github.prepayments.service.dto.SupplierDataEntryFileDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link SupplierDataEntryFile} and its DTO {@link SupplierDataEntryFileDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SupplierDataEntryFileMapper extends EntityMapper<SupplierDataEntryFileDTO, SupplierDataEntryFile> {


    default SupplierDataEntryFile fromId(Long id) {
        if (id == null) {
            return null;
        }
        SupplierDataEntryFile supplierDataEntryFile = new SupplierDataEntryFile();
        supplierDataEntryFile.setId(id);
        return supplierDataEntryFile;
    }
}
