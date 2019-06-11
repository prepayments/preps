package io.github.prepayments.app.messaging.data_entry.config;

import io.github.prepayments.app.messaging.data_entry.ServiceOutletDataStreams;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(ServiceOutletDataStreams.class)
public class ServiceOutletDataStreamsConfig {
}
