package io.github.prepayments.app.messaging.filing.listener;

import io.github.prepayments.app.messaging.PersistenceDataStreamListener;
import io.github.prepayments.app.messaging.filing.streams.FilingTransactionAccountDataEntryStreams;
import io.github.prepayments.app.messaging.filing.vm.TransactionAccountEVM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class PersistenceTransactionAccountStreamsListener implements PersistenceDataStreamListener<TransactionAccountEVM> {

    @StreamListener(FilingTransactionAccountDataEntryStreams.INPUT)
    public void handleDataStreamItem(@Payload TransactionAccountEVM transactionAccountEVM) {
        log.info("Received transactionAccountEVM at index #: {}", transactionAccountEVM.getRowIndex());
    }
}
