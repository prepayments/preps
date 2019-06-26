package io.github.prepayments.app.messaging.notifications.config;

import io.github.prepayments.app.messaging.notifications.AmortizationUploadFileUploadStreams;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(AmortizationUploadFileUploadStreams.class)
public class AmortizationUploadFileUploadStreamsConfig {
}
