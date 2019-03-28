package com.example.demo.config;

import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

	@Bean
	public Properties kafkaProperties() {
		Properties kafkaProps = new Properties();
		kafkaProps.put("bootstrap.servers", "broker1:9092,broker2:9092");
		kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		return kafkaProps;
	}
	
	@Bean
	public KafkaProducer<String, String> kafkaProducer()
	{
		return new KafkaProducer<>(kafkaProperties());
	}
	
	@Bean
	public KafkaConsumer<String, String> kafkaConsumer()
	{
		return new KafkaConsumer<>(kafkaProperties());
	}

}
