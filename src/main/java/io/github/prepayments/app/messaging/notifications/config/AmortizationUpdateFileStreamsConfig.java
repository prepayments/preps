package io.github.prepayments.app.messaging.notifications.config;

import io.github.prepayments.app.messaging.notifications.AmortizationUpdateFileStreams;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(AmortizationUpdateFileStreams.class)
public class AmortizationUpdateFileStreamsConfig {
}
