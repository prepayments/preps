package io.github.prepayments.app.messaging.data_entry;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * This describes data streams from uploaded amortization data entry files
 */
public interface AmortizationEntryStreams {

    String INPUT = "amortization-data-entry-in";
    String OUTPUT = "amortization-data-entry-out";

    /**
     * Defines an inbound stream to read messages from a Kafka topic
     */
    @Input(INPUT)
    SubscribableChannel inboundAmortizationEntries();

    /**
     * Outbound stream to write messages to a kafka topic
     */
    @Output(OUTPUT)
    MessageChannel outboundAmortizationEntries();
}
