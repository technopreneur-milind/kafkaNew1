package com.example.demo.controller;

import java.time.Duration;
import java.util.Iterator;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Message;

@RestController
@Path("/kafka1")
public class Kafka1Controller {

	@Autowired
	private KafkaProducer<String, String> kafkaProducer;

	@Autowired
	private KafkaConsumer<String, String> kafkaConsumer;

	@POST
	@Path("/message/{topic}")
	public String sendMessage(@PathParam("topic") String topic, Message message) {

		kafkaProducer.send(new ProducerRecord<String, String>(topic, message.getContent()));
		return "Successful";
	}

	@GET
	@Path("/message/{topic}")
	public ResponseEntity<Iterator<ConsumerRecord<String, String>>> consumeMessage(@PathParam("topic") String topic) {
		ConsumerRecords<String, String> allrecords = kafkaConsumer.poll(Duration.ZERO);
		Iterator<ConsumerRecord<String, String>> topicRecords = allrecords.records(topic).iterator();
		return new ResponseEntity<Iterator<ConsumerRecord<String, String>>>(topicRecords, HttpStatus.OK);
	}

}
