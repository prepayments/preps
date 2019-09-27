package io.github.prepayments.service.mapper;

import io.github.prepayments.domain.*;
import io.github.prepayments.service.dto.UpdatedAmortizationEntryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UpdatedAmortizationEntry} and its DTO {@link UpdatedAmortizationEntryDTO}.
 */
@Mapper(componentModel = "spring", uses = {PrepaymentEntryMapper.class})
public interface UpdatedAmortizationEntryMapper extends EntityMapper<UpdatedAmortizationEntryDTO, UpdatedAmortizationEntry> {

    @Mapping(source = "prepaymentEntry.id", target = "prepaymentEntryId")
    UpdatedAmortizationEntryDTO toDto(UpdatedAmortizationEntry updatedAmortizationEntry);

    @Mapping(source = "prepaymentEntryId", target = "prepaymentEntry")
    UpdatedAmortizationEntry toEntity(UpdatedAmortizationEntryDTO updatedAmortizationEntryDTO);

    default UpdatedAmortizationEntry fromId(Long id) {
        if (id == null) {
            return null;
        }
        UpdatedAmortizationEntry updatedAmortizationEntry = new UpdatedAmortizationEntry();
        updatedAmortizationEntry.setId(id);
        return updatedAmortizationEntry;
    }
}
