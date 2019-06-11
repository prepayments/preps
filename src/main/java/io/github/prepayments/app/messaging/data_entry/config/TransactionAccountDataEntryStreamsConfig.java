package io.github.prepayments.app.messaging.data_entry.config;

import io.github.prepayments.app.messaging.data_entry.TransactionAccountDataEntryStreams;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(TransactionAccountDataEntryStreams.class)
public class TransactionAccountDataEntryStreamsConfig {
}
