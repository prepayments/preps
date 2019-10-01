package io.github.prepayments.app.messaging.filing.config;

import io.github.prepayments.app.messaging.filing.streams.FilingAmortizationUpdateStreams;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(FilingAmortizationUpdateStreams.class)
public class AmortizationUpdateStreamsFilingConfig {
}
