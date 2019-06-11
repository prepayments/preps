package io.github.prepayments.app.messaging.data_entry.config;

import io.github.prepayments.app.messaging.data_entry.PrepaymentEntryStreams;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(PrepaymentEntryStreams.class)
public class PrepaymentEntryStreamsConfig {
}
