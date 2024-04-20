package by.vadzimmatsiushonak.bank.api.listener;

import by.vadzimmatsiushonak.bank.api.model.payload.LoggingPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoggingListener {

    private final static String FIRST_PARTITION_PATTERN = "[1L|{}p] : {} : {}";
    private final static String SECOND_PARTITION_PATTERN = "[2L|{}p] : {} : {}";

    @KafkaListener(id = "loggerFirstPartition",
        topicPartitions = @TopicPartition(topic = "${spring.kafka.topic.logging}", partitions = {"0", "1"}),
        containerFactory = "loggingKafkaListenerContainerFactory")
    public void loggerFirstPartition(@Payload LoggingPayload payload,
        @Header(KafkaHeaders.RECEIVED_PARTITION) String partition) {
        proceedLog(payload, FIRST_PARTITION_PATTERN, partition);
    }

    @KafkaListener(id = "loggerSecondPartition",
        topicPartitions = @TopicPartition(topic = "${spring.kafka.topic.logging}", partitions = {"2", "3"}),
        containerFactory = "loggingKafkaListenerContainerFactory")
    public void loggerSecondPartition(@Payload LoggingPayload payload,
        @Header(KafkaHeaders.RECEIVED_PARTITION) String partition) {
        proceedLog(payload, SECOND_PARTITION_PATTERN, partition);
    }

    private void proceedLog(LoggingPayload payload, String pattern, String partition) {
        switch (payload.getType()) {
            case INFO:
                log.info(pattern, partition, payload.getType(), payload.getMessage());
                break;
            case DEBUG:
                log.debug(pattern, partition, payload.getType(), payload.getMessage());
                break;
            case ERROR:
                log.error(pattern, partition, payload.getType(), payload.getMessage());
                break;
            default:
                log.info(pattern, partition, payload.getType(), payload.getMessage());
                break;
        }
    }

}
