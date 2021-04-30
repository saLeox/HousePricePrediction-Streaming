package com.gof.springcloud.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gof.springcloud.entity.ResultVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api("Kafka Producer Controller")
public class ProducerController {
	private Logger log = LoggerFactory.getLogger(ProducerController.class);

	@Value(value = "${kafka.topic.house_transaction}")
	private String topic_house_transaction;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@PostMapping("/sendHouseTransaction")
	@ApiOperation(value = "Sent a house transaction record")
	public ResultVo<String> sendHouseTransaction(String msg) {
		kafkaTemplate.send(topic_house_transaction, msg);
		return new ResultVo<String>(true);
	}

}
