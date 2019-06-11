package io.github.prepayments.service.mapper;

import io.github.prepayments.domain.*;
import io.github.prepayments.service.dto.TransactionAccountDataEntryFileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TransactionAccountDataEntryFile} and its DTO {@link TransactionAccountDataEntryFileDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TransactionAccountDataEntryFileMapper extends EntityMapper<TransactionAccountDataEntryFileDTO, TransactionAccountDataEntryFile> {



    default TransactionAccountDataEntryFile fromId(Long id) {
        if (id == null) {
            return null;
        }
        TransactionAccountDataEntryFile transactionAccountDataEntryFile = new TransactionAccountDataEntryFile();
        transactionAccountDataEntryFile.setId(id);
        return transactionAccountDataEntryFile;
    }
}
