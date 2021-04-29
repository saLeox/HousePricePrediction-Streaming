package com.gof.springcloud.bigquery;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;

@Configuration
public class BigQueryBean {

	@Bean
	public BigQuery bigQuery() throws IOException {
		ClassLoader classLoader = this.getClass().getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream("bigquery.json");
		BigQuery bigquery = BigQueryOptions.newBuilder().setCredentials(GoogleCredentials.fromStream(inputStream))
				.build().getService();
		return bigquery;
	}
}
