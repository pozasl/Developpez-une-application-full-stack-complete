package com.openclassrooms.mdd.subscribtions_api.configuration;

import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.openclassrooms.mdd.subscribtions_api.model.FeedPostModel;

import reactor.kafka.sender.SenderOptions;

/**
 * Kafka feed's post Producer Config
 */
@Configuration
public class KafkaProducerConfig {

    @Value(value = "${kafka.host}")
    private String kafkaHost;

    /**
     * Instantiate a Reactive KafkaProducerTemplate
     *
     * @param properties Default kafka properties
     * @return a KafkaProducerTemplate
     */
    @Bean
    public ReactiveKafkaProducerTemplate<String, FeedPostModel> reactiveKafkaProducer(KafkaProperties properties) {
        Map<String, Object> props = properties.buildProducerProperties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaHost + ":9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new ReactiveKafkaProducerTemplate<String, FeedPostModel>(SenderOptions.create(props));
    }
    
}
