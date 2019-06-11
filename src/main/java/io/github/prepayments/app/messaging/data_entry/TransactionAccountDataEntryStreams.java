package io.github.prepayments.app.messaging.data_entry;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface TransactionAccountDataEntryStreams {

    String INPUT = "account-data-entry-in";
    String OUTPUT = "account-data-entry-out";

    /**
     * Defines an inbound stream to read messages from a Kafka topic
     */
    @Input(INPUT)
    SubscribableChannel inboundTransactionAccountDataStream();

    /**
     * Outbound stream to write messages to a kafka topic
     */
    @Output(OUTPUT)
    MessageChannel outboundTransactionAccountDataStream();
}
