package io.github.prepayments.app.messaging.filing.listener;

import io.github.prepayments.app.messaging.filing.streams.FilingPrepaymentEntryStreams;
import io.github.prepayments.app.messaging.filing.vm.PrepaymentEntryEVM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class PersistencePrepaymentsStreamsListener implements PersistenceDataStreamListener<PrepaymentEntryEVM> {

    @StreamListener(FilingPrepaymentEntryStreams.INPUT)
    public void handleDataStreamItem(@Payload PrepaymentEntryEVM prepaymentEntryEVM) {
        log.info("Received prepaymentEntry @ index #: {}", prepaymentEntryEVM.getRowIndex());
    }

}
