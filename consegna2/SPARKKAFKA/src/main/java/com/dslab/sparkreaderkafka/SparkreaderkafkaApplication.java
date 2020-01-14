package com.dslab.sparkreaderkafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.StreamingContext;
import org.apache.spark.streaming.dstream.InputDStream;
import org.apache.spark.streaming.kafka010.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class SparkreaderkafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SparkreaderkafkaApplication.class, args);

        SparkConf conf = new SparkConf().setAppName("sparkreader").setMaster("spark://spark-master:7077");
        StreamingContext ssc = new StreamingContext(conf, Durations.seconds(30));

        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", "kafkaa:9092");
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        kafkaParams.put("group.id", "spark-group");
        kafkaParams.put("auto.offset.reset", "latest");
        kafkaParams.put("enable.auto.commit", false);


        Collection<String> topics = Arrays.asList("spout-topic");
        InputDStream<ConsumerRecord<String, String>> stream = KafkaUtils.createDirectStream(
                ssc,
                LocationStrategies.PreferConsistent(), //Location strategy
                ConsumerStrategies.<String, String>Subscribe(topics, kafkaParams)
        );


        ssc.start();
        ssc.awaitTermination();

    }

}
