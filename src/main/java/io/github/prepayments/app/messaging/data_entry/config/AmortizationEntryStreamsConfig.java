package io.github.prepayments.app.messaging.data_entry.config;

import io.github.prepayments.app.messaging.data_entry.AmortizationEntryStreams;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(AmortizationEntryStreams.class)
public class AmortizationEntryStreamsConfig {
}
