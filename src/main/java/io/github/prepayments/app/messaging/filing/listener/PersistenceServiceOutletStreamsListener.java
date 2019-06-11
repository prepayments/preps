package io.github.prepayments.app.messaging.filing.listener;

import io.github.prepayments.app.messaging.filing.streams.FilingServiceOutletDataStreams;
import io.github.prepayments.app.messaging.filing.vm.ServiceOutletEVM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class PersistenceServiceOutletStreamsListener implements PersistenceDataStreamListener<ServiceOutletEVM> {

    @StreamListener(FilingServiceOutletDataStreams.INPUT)
    public void handleDataStreamItem(@Payload ServiceOutletEVM serviceOutletEVM) {
        log.info("Received serviceOutletDataEntry @ index #: {}", serviceOutletEVM.getRowIndex());
    }
}
