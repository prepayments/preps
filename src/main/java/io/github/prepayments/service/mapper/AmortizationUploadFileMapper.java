package io.github.prepayments.service.mapper;

import io.github.prepayments.domain.*;
import io.github.prepayments.service.dto.AmortizationUploadFileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AmortizationUploadFile} and its DTO {@link AmortizationUploadFileDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AmortizationUploadFileMapper extends EntityMapper<AmortizationUploadFileDTO, AmortizationUploadFile> {



    default AmortizationUploadFile fromId(Long id) {
        if (id == null) {
            return null;
        }
        AmortizationUploadFile amortizationUploadFile = new AmortizationUploadFile();
        amortizationUploadFile.setId(id);
        return amortizationUploadFile;
    }
}
