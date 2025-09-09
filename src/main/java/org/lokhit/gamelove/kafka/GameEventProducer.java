package org.lokhit.gamelove.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;


@Component
public class GameEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final Logger log = LoggerFactory.getLogger(GameEventProducer.class);

    public GameEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendGameEvent(Long gameId, String gameName, String eventType) {

        String payload = eventType + ":" + gameId + ":" + gameName;

        ProducerRecord<String, String> record = new ProducerRecord<>("game-events", payload);
        record.headers().add(new RecordHeader("event-type", eventType.getBytes(StandardCharsets.UTF_8)));
        record.headers().add(new RecordHeader("event-source", "game-service".getBytes(StandardCharsets.UTF_8)));

        kafkaTemplate.send(record).whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Kafka send failed, redirecting to DLT", ex);

                // Fallback do dead-letter topic
                ProducerRecord<String, String> dltRecord = new ProducerRecord<>("game-events-dlt", payload);
                dltRecord.headers().add(new RecordHeader("original-event-type", eventType.getBytes(StandardCharsets.UTF_8)));
                dltRecord.headers().add(new RecordHeader("event-source", "game-service".getBytes(StandardCharsets.UTF_8)));

                kafkaTemplate.send(dltRecord);
            } else {
                log.info("Kafka event sent successfully: {}", payload);
            }
        });
    }
}




