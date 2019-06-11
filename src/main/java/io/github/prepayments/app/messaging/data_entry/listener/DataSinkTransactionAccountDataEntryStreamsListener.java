package io.github.prepayments.app.messaging.data_entry.listener;

import io.github.prepayments.app.messaging.data_entry.mapper.TransactionAccountDataEntryFileDTOMapper;
import io.github.prepayments.app.messaging.data_entry.vm.TransactionAccountEVM;
import io.github.prepayments.app.messaging.filing.streams.FilingTransactionAccountDataEntryStreams;
import io.github.prepayments.service.TransactionAccountService;
import io.github.prepayments.service.dto.TransactionAccountDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataSinkTransactionAccountDataEntryStreamsListener {

    private final TransactionAccountService transactionAccountService;
    private final TransactionAccountDataEntryFileDTOMapper transactionAccountDataEntryFileDTOMapper;

    public DataSinkTransactionAccountDataEntryStreamsListener(final TransactionAccountService transactionAccountService,
                                                              final TransactionAccountDataEntryFileDTOMapper transactionAccountDataEntryFileDTOMapper) {
        this.transactionAccountService = transactionAccountService;
        this.transactionAccountDataEntryFileDTOMapper = transactionAccountDataEntryFileDTOMapper;
    }

    @StreamListener(FilingTransactionAccountDataEntryStreams.INPUT)
    public void handleTransactionAccountUploaded(@Payload TransactionAccountEVM transactionAccountEVM) {
        log.debug("Received transactionAccountEVM at index #: {} standby for persistence...", transactionAccountEVM.getRowIndex());

        TransactionAccountDTO dto = transactionAccountDataEntryFileDTOMapper.toDTO(transactionAccountEVM);

        TransactionAccountDTO result = transactionAccountService.save(dto);

        log.debug("TransactionAccount item id # : {} persisted to DB", result.getId());
    }
}
