package io.github.prepayments.service.mapper;

import io.github.prepayments.domain.*;
import io.github.prepayments.service.dto.AmortizationUpdateFileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AmortizationUpdateFile} and its DTO {@link AmortizationUpdateFileDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AmortizationUpdateFileMapper extends EntityMapper<AmortizationUpdateFileDTO, AmortizationUpdateFile> {



    default AmortizationUpdateFile fromId(Long id) {
        if (id == null) {
            return null;
        }
        AmortizationUpdateFile amortizationUpdateFile = new AmortizationUpdateFile();
        amortizationUpdateFile.setId(id);
        return amortizationUpdateFile;
    }
}
