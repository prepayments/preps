package io.github.prepayments.service.mapper;

import io.github.prepayments.domain.*;
import io.github.prepayments.service.dto.AmortizationUploadDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AmortizationUpload} and its DTO {@link AmortizationUploadDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AmortizationUploadMapper extends EntityMapper<AmortizationUploadDTO, AmortizationUpload> {



    default AmortizationUpload fromId(Long id) {
        if (id == null) {
            return null;
        }
        AmortizationUpload amortizationUpload = new AmortizationUpload();
        amortizationUpload.setId(id);
        return amortizationUpload;
    }
}
