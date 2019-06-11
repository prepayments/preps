package io.github.prepayments.app.messaging.data_entry.config;

import io.github.prepayments.app.messaging.data_entry.SupplierDataEntryStreams;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(SupplierDataEntryStreams.class)
public class SupplierDataEntryStreamsConfig {
}
