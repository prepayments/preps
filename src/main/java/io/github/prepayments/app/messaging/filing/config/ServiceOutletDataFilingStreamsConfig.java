package io.github.prepayments.app.messaging.filing.config;

import io.github.prepayments.app.messaging.filing.streams.FilingServiceOutletDataStreams;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(FilingServiceOutletDataStreams.class)
public class ServiceOutletDataFilingStreamsConfig {
}
