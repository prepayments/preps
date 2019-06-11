package io.github.prepayments.app.messaging.filing.config;

import io.github.prepayments.app.messaging.filing.streams.FilingPrepaymentEntryStreams;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(FilingPrepaymentEntryStreams.class)
public class PrepaymentEntryStreamsFilingConfig {
}
