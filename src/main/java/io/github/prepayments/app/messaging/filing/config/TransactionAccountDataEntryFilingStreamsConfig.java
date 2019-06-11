package io.github.prepayments.app.messaging.filing.config;

import io.github.prepayments.app.messaging.filing.streams.FilingTransactionAccountDataEntryStreams;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(FilingTransactionAccountDataEntryStreams.class)
public class TransactionAccountDataEntryFilingStreamsConfig {
}
