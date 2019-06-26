package io.github.prepayments.app.messaging.filing.listener;

import io.github.prepayments.app.messaging.filing.streams.FilingAmortizationUploadStreams;
import io.github.prepayments.app.messaging.filing.vm.AmortizationUploadEVM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service
public class PersistenceAmortizationUploadsStreamsListener {

    @StreamListener(FilingAmortizationUploadStreams.INPUT)
    public void handleDataStreamItem(@Payload AmortizationUploadEVM amortizationUploadEVM) {
        log.debug("Received amortization upload index #: {}", amortizationUploadEVM.getRowIndex());
    }
}
