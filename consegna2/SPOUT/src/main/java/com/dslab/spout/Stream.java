/*package com.dslab.spout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Service
public  class Stream {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Value(value = "${KAFKA_SPOUT_TOPIC}")
    private String kafkaSpoutTopic;

     //query prom per prendere i dati da analizzare
     String url = "http://prometheus-external:9090/api/v1/query?query=(sum(gateway_requests_seconds_count)/sum(process_uptime_seconds))";
     String url1 = "http://prometheus-external:9090/api/v1/query?query=(sum(gateway_requests_seconds_sum))/(sum(gateway_requests_seconds_count))";
     RestTemplate restTemplate = new RestTemplate();


    public void send() {

        RespoProm result = restTemplate.getForObject(url, RespoProm.class);
        RespoProm result2 = restTemplate.getForObject(url1, RespoProm.class);

        if (result != null && result2 != null) {
            System.out.println(result.getData().getResult()[0].getValue()[1]);
            System.out.println(result2.getData().getResult()[0].getValue()[1]);

            String richiestesecondo = result.getData().getResult()[0].getValue()[1];
            String tempirisposta = result2.getData().getResult()[0].getValue()[1];
            System.out.println(richiestesecondo + "    " + tempirisposta);

            kafkaTemplate.send(kafkaSpoutTopic, "rs|" + richiestesecondo);
            System.out.println(richiestesecondo + "    " + tempirisposta);
        }else {
            System.out.println("no data");
        }
    }

}
*/