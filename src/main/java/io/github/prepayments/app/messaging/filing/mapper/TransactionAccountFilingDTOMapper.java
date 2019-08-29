package io.github.prepayments.app.messaging.filing.mapper;

import io.github.prepayments.app.messaging.DtoMapper;
import io.github.prepayments.app.messaging.filing.vm.TransactionAccountEVM;
import io.github.prepayments.service.dto.TransactionAccountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {})
public interface TransactionAccountFilingDTOMapper extends DtoMapper<TransactionAccountEVM, TransactionAccountDTO> {

    default TransactionAccountDTO fromId(Long id) {
        if (id == null) {
            return null;
        }
        TransactionAccountDTO transactionAccountDTO = new TransactionAccountDTO();
        transactionAccountDTO.setId(id);
        return transactionAccountDTO;
    }

    @Mappings( {@Mapping(target = "openingDate", dateFormat = "yyyy/MM/dd"),})
    @Override
    TransactionAccountDTO toDTO(TransactionAccountEVM simpleTransactionAccountEVM);

    @Mappings( {@Mapping(target = "openingDate", dateFormat = "yyyy/MM/dd"),})
    @Override
    TransactionAccountEVM toEVM(TransactionAccountDTO transactionAccountDTO);
}
