package io.github.prepayments.service.mapper;

import io.github.prepayments.domain.TransactionAccount;
import io.github.prepayments.service.dto.TransactionAccountDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link TransactionAccount} and its DTO {@link TransactionAccountDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TransactionAccountMapper extends EntityMapper<TransactionAccountDTO, TransactionAccount> {


    default TransactionAccount fromId(Long id) {
        if (id == null) {
            return null;
        }
        TransactionAccount transactionAccount = new TransactionAccount();
        transactionAccount.setId(id);
        return transactionAccount;
    }
}
