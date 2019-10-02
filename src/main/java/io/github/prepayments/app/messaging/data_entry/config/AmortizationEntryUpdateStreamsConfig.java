package io.github.prepayments.app.messaging.data_entry.config;

import io.github.prepayments.app.messaging.data_entry.AmortizationEntryUpdateStreams;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(AmortizationEntryUpdateStreams.class)
public class AmortizationEntryUpdateStreamsConfig {
}
