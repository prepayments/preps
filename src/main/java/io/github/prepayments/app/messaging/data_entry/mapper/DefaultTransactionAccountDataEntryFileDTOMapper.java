package io.github.prepayments.app.messaging.data_entry.mapper;

import io.github.prepayments.app.messaging.data_entry.vm.SimpleTransactionAccountEVM;
import io.github.prepayments.app.messaging.data_entry.vm.SimpleTransactionAccountEVM.SimpleTransactionAccountEVMBuilder;
import io.github.prepayments.service.dto.TransactionAccountDTO;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static io.github.prepayments.domain.enumeration.AccountTypes.AMORTIZATION;
import static io.github.prepayments.domain.enumeration.AccountTypes.PREPAYMENT;

@Primary
@Component
public class DefaultTransactionAccountDataEntryFileDTOMapper implements TransactionAccountDataEntryFileDTOMapper {

    @Override
    public List<TransactionAccountDTO> toDTO(List<SimpleTransactionAccountEVM> evmList) {
        if (evmList == null) {
            return null;
        }

        List<TransactionAccountDTO> list = new ArrayList<TransactionAccountDTO>(evmList.size());
        for (SimpleTransactionAccountEVM simpleTransactionAccountEVM : evmList) {
            list.add(toDTO(simpleTransactionAccountEVM));
        }

        return list;
    }

    @Override
    public List<SimpleTransactionAccountEVM> toEVM(List<TransactionAccountDTO> entityList) {
        if (entityList == null) {
            return null;
        }

        List<SimpleTransactionAccountEVM> list = new ArrayList<SimpleTransactionAccountEVM>(entityList.size());
        for (TransactionAccountDTO transactionAccountDTO : entityList) {
            list.add(toEVM(transactionAccountDTO));
        }

        return list;
    }

    @Override
    public TransactionAccountDTO toDTO(SimpleTransactionAccountEVM simpleTransactionAccountEVM) {
        if (simpleTransactionAccountEVM == null) {
            return null;
        }

        TransactionAccountDTO transactionAccountDTO = new TransactionAccountDTO();

        transactionAccountDTO.setAccountName(simpleTransactionAccountEVM.getAccountName());
        transactionAccountDTO.setAccountNumber(simpleTransactionAccountEVM.getAccountNumber());
        if (simpleTransactionAccountEVM.getAccountType().equalsIgnoreCase("PREPAYMENT")) {
            transactionAccountDTO.setAccountType(PREPAYMENT);
        }
        if (simpleTransactionAccountEVM.getAccountType().equalsIgnoreCase("AMORTIZATION")) {
            transactionAccountDTO.setAccountType(AMORTIZATION);
        }
        if (simpleTransactionAccountEVM.getOpeningDate() != null) {
            transactionAccountDTO.setOpeningDate(LocalDate.parse(simpleTransactionAccountEVM.getOpeningDate(), DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        }
        transactionAccountDTO.setOriginatingFileToken(simpleTransactionAccountEVM.getOriginatingFileToken());

        return transactionAccountDTO;
    }

    @Override
    public SimpleTransactionAccountEVM toEVM(TransactionAccountDTO transactionAccountDTO) {
        if (transactionAccountDTO == null) {
            return null;
        }

        SimpleTransactionAccountEVMBuilder simpleTransactionAccountEVM = SimpleTransactionAccountEVM.builder();

        simpleTransactionAccountEVM.accountName(transactionAccountDTO.getAccountName());
        simpleTransactionAccountEVM.accountNumber(transactionAccountDTO.getAccountNumber());
        if (transactionAccountDTO.getAccountType() == PREPAYMENT) {
            simpleTransactionAccountEVM.accountType("PREPAYMENT");
        }
        if (transactionAccountDTO.getAccountType() == AMORTIZATION) {
            simpleTransactionAccountEVM.accountType("AMORTIZATION");
        }
        if (transactionAccountDTO.getOpeningDate() != null) {
            simpleTransactionAccountEVM.openingDate(DateTimeFormatter.ofPattern("yyyy/MM/dd").format(transactionAccountDTO.getOpeningDate()));
        }
        simpleTransactionAccountEVM.originatingFileToken(transactionAccountDTO.getOriginatingFileToken());

        return simpleTransactionAccountEVM.build();
    }
}
