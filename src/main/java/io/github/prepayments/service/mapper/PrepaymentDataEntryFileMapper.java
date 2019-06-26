package io.github.prepayments.service.mapper;

import io.github.prepayments.domain.PrepaymentDataEntryFile;
import io.github.prepayments.service.dto.PrepaymentDataEntryFileDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link PrepaymentDataEntryFile} and its DTO {@link PrepaymentDataEntryFileDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PrepaymentDataEntryFileMapper extends EntityMapper<PrepaymentDataEntryFileDTO, PrepaymentDataEntryFile> {


    default PrepaymentDataEntryFile fromId(Long id) {
        if (id == null) {
            return null;
        }
        PrepaymentDataEntryFile prepaymentDataEntryFile = new PrepaymentDataEntryFile();
        prepaymentDataEntryFile.setId(id);
        return prepaymentDataEntryFile;
    }
}
