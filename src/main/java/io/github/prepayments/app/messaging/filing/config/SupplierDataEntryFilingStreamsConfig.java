package io.github.prepayments.app.messaging.filing.config;

import io.github.prepayments.app.messaging.filing.streams.FilingSupplierDataEntryStreams;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(FilingSupplierDataEntryStreams.class)
public class SupplierDataEntryFilingStreamsConfig {
}
