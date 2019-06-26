package io.github.prepayments.app.messaging.filing.config;

import io.github.prepayments.app.messaging.filing.streams.FilingAmortizationUploadStreams;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(FilingAmortizationUploadStreams.class)
public class AmortizationUploadStreamsFilingConfig {
}
