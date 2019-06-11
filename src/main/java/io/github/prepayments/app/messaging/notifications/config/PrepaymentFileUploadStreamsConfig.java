package io.github.prepayments.app.messaging.notifications.config;

import io.github.prepayments.app.messaging.notifications.PrepaymentFileUploadStreams;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(PrepaymentFileUploadStreams.class)
public class PrepaymentFileUploadStreamsConfig {
}
