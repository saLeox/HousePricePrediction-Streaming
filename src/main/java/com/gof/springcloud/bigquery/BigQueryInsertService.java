package com.gof.springcloud.bigquery;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.InsertAllRequest;
import com.google.cloud.bigquery.InsertAllResponse;
import com.google.cloud.bigquery.TableId;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BigQueryInsertService {

	@Autowired
	private BigQuery bigQuery;
	@Value(value = "${bigquery.dataset}")
	private String dataset;
	@Value(value = "${bigquery.table}")
	private String table;

	// refer to following link
	// https://cloud.google.com/bigquery/streaming-data-into-bigquery#bigquery_table_insert_rows-java
	public void insert(Map<String, Object> rowContent) {
		TableId tableId = TableId.of(dataset, table); // test, train, X_train
		InsertAllResponse response = bigQuery
				.insertAll(InsertAllRequest.newBuilder(tableId).addRow(rowContent).build());
		if (response.hasErrors()) {
			log.error("BigQuery insert errors: " + response.getInsertErrors());
		}
	}

}
