package com.gof.springcloud.streams.query;

import java.time.Instant;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyWindowStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api("Interactive Query Control")
public class InteractiveQueryController {

	@Autowired
	@Qualifier(KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_BUILDER_BEAN_NAME)
	private StreamsBuilderFactoryBean factoryBean;

	@Value(value = "${kafka.stream.katble.house_transaction_cnt}")
	private String ktable;

	@GetMapping("/getTransactionLastHour")
	@ApiOperation(value = "getTransactionLastHour")
	public Long getTransactionLastHour() {
		StoreQueryParameters<ReadOnlyWindowStore<String, Long>> param = StoreQueryParameters.fromNameAndType(ktable,
				QueryableStoreTypes.windowStore());
		ReadOnlyWindowStore<String, Long> windowStore = factoryBean.getKafkaStreams().store(param);
		Instant timeTo = Instant.now(); // now (in processing-time)
		Instant timeFrom = timeTo.minusSeconds(60 * 60); // beginning of time = oldest available
		KeyValueIterator<Long, Long> iterator = windowStore.fetch("transaction", timeFrom, timeTo);
		Long res = (long) 0;
		while (iterator.hasNext()) {
			KeyValue<Long, Long> next = iterator.next();
			long windowTimestamp = next.key;
			System.out.println("Count of 'world' @ time " + windowTimestamp + " is " + next.value);
			res += next.value;
		}
		return res;
	}

}
