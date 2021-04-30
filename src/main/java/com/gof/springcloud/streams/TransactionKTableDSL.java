package com.gof.springcloud.streams;

import java.time.Duration;

import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.state.WindowStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;

import com.gof.springcloud.bigquery.BigQueryInsertService;
import com.gof.springcloud.etl.JsonExtractUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class TransactionKTableDSL {
	@Autowired
	private BigQueryInsertService bigQueryInsert;

	@Value(value = "${kafka.topic.house_transaction}")
	private String topic;

	@Value(value = "${kafka.stream.katble.house_transaction_cnt}")
	private String ktable;

	@Bean("transactionETLStream")
	// By default, window here is Tumbling.
	// The other two can be set specifically
	// https://kafka.apache.org/28/documentation/streams/developer-guide/dsl-api.html#hopping-time-windows
	public KStream<String, String> windowExampleProcessing(
			@Qualifier(KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_BUILDER_BEAN_NAME) StreamsBuilder builder) {
		KStream<String, String> stream = builder.stream(topic);
		stream.foreach((key, value) -> bigQueryInsert
				.insert(JsonExtractUtil.extractFromStr(value, JsonExtractUtil.transaction_feature)));
		stream.map((key, value) -> new KeyValue<String, String>("transaction", value.toString())).groupByKey()
				.windowedBy(TimeWindows.of(Duration.ofMinutes(10)))
				.count(Materialized.<String, Long, WindowStore<Bytes, byte[]>>as(ktable));
		return stream;
	}

}
