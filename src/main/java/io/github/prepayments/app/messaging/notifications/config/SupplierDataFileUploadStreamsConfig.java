package io.github.prepayments.app.messaging.notifications.config;

import io.github.prepayments.app.messaging.notifications.SupplierDataFileUploadStreams;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(SupplierDataFileUploadStreams.class)
public class SupplierDataFileUploadStreamsConfig {
}
