package io.github.prepayments.app.messaging.notifications.config;

import io.github.prepayments.app.messaging.notifications.ServiceOutletFileUploadStreams;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(ServiceOutletFileUploadStreams.class)
public class ServiceOutletFileUploadStreamsConfig {
}
