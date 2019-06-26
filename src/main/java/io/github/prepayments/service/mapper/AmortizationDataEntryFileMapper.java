package io.github.prepayments.service.mapper;

import io.github.prepayments.domain.AmortizationDataEntryFile;
import io.github.prepayments.service.dto.AmortizationDataEntryFileDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link AmortizationDataEntryFile} and its DTO {@link AmortizationDataEntryFileDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AmortizationDataEntryFileMapper extends EntityMapper<AmortizationDataEntryFileDTO, AmortizationDataEntryFile> {


    default AmortizationDataEntryFile fromId(Long id) {
        if (id == null) {
            return null;
        }
        AmortizationDataEntryFile amortizationDataEntryFile = new AmortizationDataEntryFile();
        amortizationDataEntryFile.setId(id);
        return amortizationDataEntryFile;
    }
}
