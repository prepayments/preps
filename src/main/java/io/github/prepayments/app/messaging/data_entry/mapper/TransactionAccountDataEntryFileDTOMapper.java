package io.github.prepayments.app.messaging.data_entry.mapper;

import io.github.prepayments.app.messaging.DtoMapper;
import io.github.prepayments.app.messaging.data_entry.vm.SimpleTransactionAccountEVM;
import io.github.prepayments.service.dto.TransactionAccountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {})
public interface TransactionAccountDataEntryFileDTOMapper extends DtoMapper<SimpleTransactionAccountEVM, TransactionAccountDTO> {

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
    TransactionAccountDTO toDTO(SimpleTransactionAccountEVM simpleTransactionAccountEVM);

    @Mappings( {@Mapping(target = "openingDate", dateFormat = "yyyy/MM/dd"),})
    @Override
    SimpleTransactionAccountEVM toEVM(TransactionAccountDTO transactionAccountDTO);
}
