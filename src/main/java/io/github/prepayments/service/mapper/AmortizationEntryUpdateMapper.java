package io.github.prepayments.service.mapper;

import io.github.prepayments.domain.*;
import io.github.prepayments.service.dto.AmortizationEntryUpdateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AmortizationEntryUpdate} and its DTO {@link AmortizationEntryUpdateDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AmortizationEntryUpdateMapper extends EntityMapper<AmortizationEntryUpdateDTO, AmortizationEntryUpdate> {



    default AmortizationEntryUpdate fromId(Long id) {
        if (id == null) {
            return null;
        }
        AmortizationEntryUpdate amortizationEntryUpdate = new AmortizationEntryUpdate();
        amortizationEntryUpdate.setId(id);
        return amortizationEntryUpdate;
    }
}
