package com.openclassrooms.mdd.subscribtions_api.configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.openclassrooms.mdd.api.model.Post;

import reactor.kafka.receiver.ReceiverOptions;

/**
 * Kafka posts consumer configuration
 */
@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${kafka.host}")
    private String kafkaHost;

    /**
     * Kafka receiver options
     *
     * @param topic Posts's topic
     * @param kafkaProperties Default kafka properties
     * @return Receiver options
     */
    @Bean
    public ReceiverOptions<String, Post> kafkaReceiverOptions(@Value(value = "${topic.posts.name}") String topic,
            KafkaProperties kafkaProperties) {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaHost + ":9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "mdd-posts-grp");
        config.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.openclassrooms.mdd.api.model.Post");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        ReceiverOptions<String, Post> basicReceiverOptions = ReceiverOptions.create(config);
        return basicReceiverOptions.subscription(Collections.singletonList(topic));
    }

    /**
     * Instantiate a ReactiveKafkaConsumerTemplate
     *
     * @param kafkaReceiverOptions Receiver options
     * @return a ReactiveKafkaConsumerTemplate
     */
    @Bean
    public ReactiveKafkaConsumerTemplate<String, Post> reactiveKafkaConsumer(
            ReceiverOptions<String, Post> kafkaReceiverOptions) {
        return new ReactiveKafkaConsumerTemplate<String, Post>(kafkaReceiverOptions);
    }

}
