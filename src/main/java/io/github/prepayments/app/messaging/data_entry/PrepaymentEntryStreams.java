package io.github.prepayments.app.messaging.data_entry;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface PrepaymentEntryStreams {

    String INPUT = "prepayment-data-entry-in";
    String OUTPUT = "prepayment-data-entry-out";

    /**
     * Defines an inbound stream to read messages from a Kafka topic
     */
    @Input(INPUT)
    SubscribableChannel inboundPrepaymentEntries();

    /**
     * Outbound stream to write messages to a kafka topic
     */
    @Output(OUTPUT)
    MessageChannel outboundPrepaymentEntries();
}
