package io.github.prepayments.app.messaging.notifications;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface SupplierDataFileUploadStreams {


    String FILE_INPUT = "supplier-entry-file-uploaded-in";
    String FILE_OUTPUT = "supplier-entry-file-uploaded-out";

    /**
     * Defines an inbound stream to read messages from a Kafka topic
     */
    @Input(FILE_INPUT)
    SubscribableChannel inboundSupplierFileUploads();

    /**
     * Outbound stream to write messages to a kafka topic
     */
    @Output(FILE_OUTPUT)
    MessageChannel outboundSupplierFileUploads();
}
