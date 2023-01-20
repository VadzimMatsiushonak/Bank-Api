package by.vadzimmatsiushonak.bank.api.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${spring.kafka.topic.logging}")
    private String loggingTopic;

    @Bean
    public NewTopic loggingTopic() {
        return TopicBuilder.name(loggingTopic)
            .partitions(4)
            .replicas(1)
            .build();
    }

}
