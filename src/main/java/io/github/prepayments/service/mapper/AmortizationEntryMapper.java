package io.github.prepayments.service.mapper;

import io.github.prepayments.domain.AmortizationEntry;
import io.github.prepayments.service.dto.AmortizationEntryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link AmortizationEntry} and its DTO {@link AmortizationEntryDTO}.
 */
@Mapper(componentModel = "spring", uses = {PrepaymentEntryMapper.class})
public interface AmortizationEntryMapper extends EntityMapper<AmortizationEntryDTO, AmortizationEntry> {

    @Mapping(source = "prepaymentEntry.id", target = "prepaymentEntryId")
    AmortizationEntryDTO toDto(AmortizationEntry amortizationEntry);

    @Mapping(source = "prepaymentEntryId", target = "prepaymentEntry")
    AmortizationEntry toEntity(AmortizationEntryDTO amortizationEntryDTO);

    default AmortizationEntry fromId(Long id) {
        if (id == null) {
            return null;
        }
        AmortizationEntry amortizationEntry = new AmortizationEntry();
        amortizationEntry.setId(id);
        return amortizationEntry;
    }
}
