package io.github.prepayments.app.messaging.notifications;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface PrepaymentFileUploadStreams {

    String FILE_INPUT = "prepayment-entry-file-uploaded-in";
    String FILE_OUTPUT = "prepayment-entry-file-uploaded-out";

    /**
     * Defines an inbound stream to read messages from a Kafka topic
     */
    @Input(FILE_INPUT)
    SubscribableChannel inboundPrepaymentFileUploads();

    /**
     * Outbound stream to write messages to a kafka topic
     */
    @Output(FILE_OUTPUT)
    MessageChannel outboundPrepaymentFileUploads();
}
