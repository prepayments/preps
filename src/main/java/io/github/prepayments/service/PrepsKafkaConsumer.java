package io.github.prepayments.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PrepsKafkaConsumer {

    private final Logger log = LoggerFactory.getLogger(PrepsKafkaConsumer.class);
    private static final String TOPIC = "topic_preps";

    @KafkaListener(topics = "topic_preps", groupId = "group_id")
    public void consume(String message) throws IOException {
        log.info("Consumed message in {} : {}", TOPIC, message);
    }
}
