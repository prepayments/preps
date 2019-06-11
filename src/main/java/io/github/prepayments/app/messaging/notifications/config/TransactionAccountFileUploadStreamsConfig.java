package io.github.prepayments.app.messaging.notifications.config;

import io.github.prepayments.app.messaging.notifications.TransactionAccountFileUploadStreams;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(TransactionAccountFileUploadStreams.class)
public class TransactionAccountFileUploadStreamsConfig {
}
