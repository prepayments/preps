package io.github.prepayments.service.mapper;

import io.github.prepayments.domain.AccountingTransaction;
import io.github.prepayments.service.dto.AccountingTransactionDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link AccountingTransaction} and its DTO {@link AccountingTransactionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AccountingTransactionMapper extends EntityMapper<AccountingTransactionDTO, AccountingTransaction> {


    default AccountingTransaction fromId(Long id) {
        if (id == null) {
            return null;
        }
        AccountingTransaction accountingTransaction = new AccountingTransaction();
        accountingTransaction.setId(id);
        return accountingTransaction;
    }
}
