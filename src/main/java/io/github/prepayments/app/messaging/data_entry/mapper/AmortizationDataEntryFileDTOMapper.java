package io.github.prepayments.app.messaging.data_entry.mapper;

import io.github.prepayments.app.messaging.DtoMapper;
import io.github.prepayments.app.messaging.data_entry.vm.SimpleAmortizationEntryEVM;
import io.github.prepayments.service.dto.AmortizationEntryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {})
public interface AmortizationDataEntryFileDTOMapper extends DtoMapper<SimpleAmortizationEntryEVM, AmortizationEntryDTO> {


    default AmortizationEntryDTO fromId(Long id) {
        if (id == null) {
            return null;
        }
        AmortizationEntryDTO amortizationEntryDTO = new AmortizationEntryDTO();
        amortizationEntryDTO.setId(id);
        return amortizationEntryDTO;
    }

    @Mappings( {@Mapping(target = "amortizationDate", dateFormat = "yyyy/MM/dd"),})
    @Override
    AmortizationEntryDTO toDTO(SimpleAmortizationEntryEVM simpleAmortizationEntryEVM);

    @Mappings( {@Mapping(target = "amortizationDate", dateFormat = "yyyy/MM/dd"),})
    @Override
    SimpleAmortizationEntryEVM toEVM(AmortizationEntryDTO amortizationEntryDTO);

    /*@Mappings({
        @Mapping(target = "amortizationDate", dateFormat = "yyyy/MM/dd"),
    })
    @Override
    List<AmortizationEntryDTO> toDTO(List<AmortizationEntryEVM> amortizationEntryEVMS);

    @Mappings({
        @Mapping(target = "amortizationDate", dateFormat = "yyyy/MM/dd"),
    })
    @Override
    List<AmortizationEntryEVM> toEVM(List<AmortizationEntryDTO> entityList);*/
}
