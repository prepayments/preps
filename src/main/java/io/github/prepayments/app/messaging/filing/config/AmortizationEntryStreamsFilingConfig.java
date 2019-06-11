package io.github.prepayments.app.messaging.filing.config;

import io.github.prepayments.app.messaging.filing.streams.FilingAmortizationEntryStreams;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(FilingAmortizationEntryStreams.class)
public class AmortizationEntryStreamsFilingConfig {
}
