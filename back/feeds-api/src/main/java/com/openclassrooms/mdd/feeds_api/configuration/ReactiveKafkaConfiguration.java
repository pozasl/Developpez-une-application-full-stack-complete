package com.openclassrooms.mdd.feeds_api.configuration;

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

import com.openclassrooms.mdd.feeds_api.model.FeedPostModel;

import reactor.kafka.receiver.ReceiverOptions;

/**
 * Reactive Kafka receiver configuration 
 */
@Configuration
public class ReactiveKafkaConfiguration {

    @Value(value = "${kafka.host}")
    private String kafkaHost;

    /**
     * Kafka receiver Options
     * @param topic Kafka topic to subscribe
     * @param kafkaProperties Default kafka properties
     * @return Receiver options
     */
    @Bean
    public ReceiverOptions<String, FeedPostModel> kafkaReceiverOptions(@Value(value = "${topic.feeds.name}") String topic, KafkaProperties kafkaProperties) {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaHost + ":9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "mdd-feed-grp");
        config.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.openclassrooms.mdd.feeds_api.model.FeedPostModel");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        ReceiverOptions<String, FeedPostModel> basicReceiverOptions = ReceiverOptions.create(config);
        return basicReceiverOptions.subscription(Collections.singletonList(topic));

    }

    /**
     * Instantiate a Reactive KafkaConsumerTemplate
     * @param kafkaReceiverOptions Receiver options
     * @return a ReactiveKafkaConsumerTemplate
     */
    @Bean
    public ReactiveKafkaConsumerTemplate<String, FeedPostModel> reactiveKafkaConsumer(
            ReceiverOptions<String, FeedPostModel> kafkaReceiverOptions) {
        return new ReactiveKafkaConsumerTemplate<String, FeedPostModel>(kafkaReceiverOptions);
    }

}
