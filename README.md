


# Lambda-Streaming-HousePricePredict

## *Architect Overview*
![](https://raw.githubusercontent.com/saLeox/photoHub/main/20210430185251.png)

**Architect Decision**

*Spark-streaming* or *Flink-streaming* need cluster to submit jobs, will not make any attempt so far.

## *Components*

### 4 main sections, 4/4 now

 - [x] 1. Kafka Producer
 - [x]  2. Kafka Consumer
 - [x]  3. Kafka Streaming
 - [Materialize](https://github.com/saLeox/HousePricePrediction-Streaming/blob/main/src/main/java/com/gof/springcloud/streams/TransactionKTableDSL.java) the KTable.
 - [Sink](https://github.com/saLeox/HousePricePrediction-Streaming/blob/main/src/main/java/com/gof/springcloud/bigquery/BigQueryInsertService.java) to Big Query using Streaming API provided by GCP.
- Handover Topology management to spring and only need to configure the connection info in one [place](https://github.com/saLeox/HousePricePrediction-Streaming/blob/main/src/main/java/com/gof/springcloud/streams/KafkaStreamsConfig.java).

 - [x] 4. InteractiveQuery to Ktable.

 - Get the time-accumulated [result](https://github.com/saLeox/HousePricePrediction-Streaming/blob/main/src/main/java/com/gof/springcloud/streams/query/InteractiveQueryController.java). 
 - Get the KafkaStreams by bean injection in Spring, refer to  [here](https://github.com/saLeox/HousePricePrediction-Streaming/blob/main/src/main/java/com/gof/springcloud/streams/query/InteractiveQueryController.java).

## *Before you deploy*

 - Prepare the kafka cluster, can follow the instruction [here](https://github.com/saLeox/kafka-cluster-docker-usage) and run
   on the top of docker. 
 - Create the topics in [yml](https://github.com/saLeox/HousePricePrediction-Streaming/blob/main/src/main/resources/application.yml) in advance.
 - Check the big query connection info whether is defined correctly in [yml](https://github.com/saLeox/HousePricePrediction-Streaming/blob/main/src/main/resources/application.yml).

## *After you deploy*
The producer to send msg and streaming-interactive-query to query are open on Swagger page, once you start this project.

The incoming topics can be viewd on [Kafdrop](http://localhost:9001/) or log.
<div align=center><img src="https://raw.githubusercontent.com/saLeox/photoHub/main/20210429203451.png" width="60%"/></div>
