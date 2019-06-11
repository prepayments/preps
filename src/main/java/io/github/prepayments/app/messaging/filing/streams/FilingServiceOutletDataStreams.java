package io.github.prepayments.app.messaging.filing.streams;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface FilingServiceOutletDataStreams {

    String INPUT = "service-outlet-data-entry-in";
    String OUTPUT = "service-outlet-data-entry-out";

    /**
     * Defines an inbound stream to read messages from a Kafka topic
     */
    @Input(INPUT)
    SubscribableChannel inboundServiceOutletDataEntries();

    /**
     * Outbound stream to write messages to a kafka topic
     */
    @Output(OUTPUT)
    MessageChannel outboundServiceOutletDataEntries();
}
