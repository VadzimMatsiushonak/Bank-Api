package by.vadzimmatsiushonak.bank.api.listener;

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
    private final static String INFO_PATTERN = "INFO";
    private final static String DEBUG_PATTERN = "DEBUG";

    @KafkaListener(id = "loggerFirstPartition",
        topicPartitions = @TopicPartition(topic = "${kafka.topic.logging}", partitions = {"0", "1"}))
    public void loggerFirstPartition(@Payload String msg, @Header(KafkaHeaders.RECEIVED_PARTITION) String partition) {
        log.info(FIRST_PARTITION_PATTERN, partition, INFO_PATTERN, msg);
    }

    @KafkaListener(id = "loggerSecondPartition",
        topicPartitions = @TopicPartition(topic = "${kafka.topic.logging}", partitions = {"2", "3"}))
    public void loggerSecondPartition(@Payload String msg, @Header(KafkaHeaders.RECEIVED_PARTITION) String partition) {
        log.info(SECOND_PARTITION_PATTERN, partition, INFO_PATTERN, msg);
    }

}
