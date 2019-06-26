package io.github.prepayments.app.messaging.data_entry.config;

import io.github.prepayments.app.messaging.data_entry.AmortizationUploadStreams;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(AmortizationUploadStreams.class)
public class AmortizationUploadsDataSinkStreamsConfig {
}
