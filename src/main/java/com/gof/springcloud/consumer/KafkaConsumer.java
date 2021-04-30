package com.gof.springcloud.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Component
public class KafkaConsumer {

	@KafkaListener(topics = "${kafka.topic.house_transaction}", groupId = "${kafka.topic.group}")
	public void consumeJson(String msg) {
		log.info("Consumed JSON Message: " + msg);
	}
}
