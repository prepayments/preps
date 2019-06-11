package io.github.prepayments.app.messaging.notifications.config;

import io.github.prepayments.app.messaging.notifications.AmortizationFileUploadStreams;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(AmortizationFileUploadStreams.class)
public class AmortizationFileUploadStreamsConfig {
}
