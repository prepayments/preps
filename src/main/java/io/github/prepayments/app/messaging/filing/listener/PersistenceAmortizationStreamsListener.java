package io.github.prepayments.app.messaging.filing.listener;

import io.github.prepayments.app.messaging.PersistenceDataStreamListener;
import io.github.prepayments.app.messaging.filing.streams.FilingAmortizationEntryStreams;
import io.github.prepayments.app.messaging.filing.vm.AmortizationEntryEVM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class PersistenceAmortizationStreamsListener implements PersistenceDataStreamListener<AmortizationEntryEVM> {

    @StreamListener(FilingAmortizationEntryStreams.INPUT)
    public void handleDataStreamItem(@Payload AmortizationEntryEVM amortizationEntryEVM) {
        log.debug("Received amortizationEntry #: {}", amortizationEntryEVM.getRowIndex());
    }

}
