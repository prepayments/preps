package io.github.prepayments.app.messaging.filing.listener;

import io.github.prepayments.app.messaging.filing.streams.FilingSupplierDataEntryStreams;
import io.github.prepayments.app.messaging.filing.vm.RegisteredSupplierEVM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class PersistenceSupplierStreamsListener implements PersistenceDataStreamListener<RegisteredSupplierEVM> {

    @Override
    @StreamListener(FilingSupplierDataEntryStreams.INPUT)
    public void handleDataStreamItem(@Payload RegisteredSupplierEVM supplierEVM) {
        log.info("Received registeredSupplierEVM at index #: {}", supplierEVM.getRowIndex());
    }
}
