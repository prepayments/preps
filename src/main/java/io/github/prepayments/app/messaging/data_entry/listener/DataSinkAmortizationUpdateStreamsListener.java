package io.github.prepayments.app.messaging.data_entry.listener;

import io.github.prepayments.app.messaging.data_entry.vm.SimpleAmortizationEntryUpdateEVM;
import io.github.prepayments.app.messaging.filing.streams.FilingAmortizationUpdateStreams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataSinkAmortizationUpdateStreamsListener {


    @StreamListener(FilingAmortizationUpdateStreams.INPUT)
    public void handleAmortizationEntryStreams(@Payload SimpleAmortizationEntryUpdateEVM simpleAmortizationEntryEVM) {
        log.trace("Received amortizationEntry update for amortizaztion id : {} dated : {} for prepayment id #: {} standby for persistence...", simpleAmortizationEntryEVM.getAmortizationEntryId(),
                  simpleAmortizationEntryEVM.getAmortizationDate(), simpleAmortizationEntryEVM.getPrepaymentEntryId());

    }
}
