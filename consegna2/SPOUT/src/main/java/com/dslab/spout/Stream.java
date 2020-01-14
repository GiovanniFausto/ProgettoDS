package com.dslab.spout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Service
public class Stream {
    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;
    @Value(value = "${KAFKA_SPOUT_TOPIC}")
    private String kafkaSpoutTopic;
    //query prom per prendere i dati da analizzare
    final String url = "http://prometheus-external:9090/api/v1/query?query=(sum(gateway_requests_seconds_count)/sum(process_uptime_seconds))";
    final String url1 = "http://prometheus-external:9090/api/v1/query?query=(sum(gateway_requests_seconds_sum))/(sum(gateway_requests_seconds_count))";
    RestTemplate restTemplate = new RestTemplate();


    public void stream(){
        String result = restTemplate.getForObject(url, String.class);
        String result2 = restTemplate.getForObject(url1, String.class);
        result = result.substring(result.lastIndexOf("value") + 8, result.length() - 5);
        result2 = result2.substring(result2.lastIndexOf("value") + 8, result2.length() - 5);
        String[] res = result.split(",");
        String[] res2 = result2.split(",");
        String value = res[1].replace("\"", "");
        String value2 = res2[1].replace("\"", "");
        Double richiestesecondo = Double.parseDouble(value);
        Double tempirisposta = Double.parseDouble(value2);
        System.out.println("richieste al secondo"+richiestesecondo);
        System.out.println("tempi di risposta"+tempirisposta);
        kafkaTemplate.send(kafkaSpoutTopic,"rs|"+richiestesecondo);
        kafkaTemplate.send(kafkaSpoutTopic,"tr|"+tempirisposta);
    }

}
