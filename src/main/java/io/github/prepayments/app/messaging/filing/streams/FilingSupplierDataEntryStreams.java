package io.github.prepayments.app.messaging.filing.streams;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface FilingSupplierDataEntryStreams {

    String INPUT = "supplier-data-entry-in";
    String OUTPUT = "supplier-data-entry-out";

    /**
     * Defines an inbound stream to read messages from a Kafka topic
     */
    @Input(INPUT)
    SubscribableChannel inboundSupplierDataStream();

    /**
     * Outbound stream to write messages to a kafka topic
     */
    @Output(OUTPUT)
    MessageChannel outboundSupplierDataStream();
}
