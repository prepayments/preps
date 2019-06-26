package io.github.prepayments.app.messaging.filing.mapper;

import io.github.prepayments.app.messaging.DtoMapper;
import io.github.prepayments.app.messaging.filing.vm.AmortizationUploadEVM;
import io.github.prepayments.service.dto.AmortizationUploadDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface AmortizationUploadFilingDTOMapper extends DtoMapper<AmortizationUploadEVM, AmortizationUploadDTO> {

    default AmortizationUploadDTO fromId(Long id) {
        if (id == null) {
            return null;
        }
        AmortizationUploadDTO amortizationUploadDTO = new AmortizationUploadDTO();
        amortizationUploadDTO.setId(id);
        return amortizationUploadDTO;
    }
}
