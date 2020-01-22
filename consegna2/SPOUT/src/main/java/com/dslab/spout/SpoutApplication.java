package com.dslab.spout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;


@SpringBootApplication
public class SpoutApplication implements CommandLineRunner {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(SpoutApplication.class, args).close();
       

    }

    @Autowired
    private  KafkaTemplate<String,String> kafkaTemplate;

    @Value(value = "${KAFKA_SPOUT_TOPIC}")
    private  String kafkaSpoutTopic;

    @Override
    public void run(String... args)throws Exception{
        String url = "http://prometheus-external:9090/api/v1/query?query=(sum(gateway_requests_seconds_count)/sum(process_uptime_seconds))";
        String url1 = "http://prometheus-external:9090/api/v1/query?query=(sum(gateway_requests_seconds_sum))/(sum(gateway_requests_seconds_count))";
        RestTemplate restTemplate = new RestTemplate();

        while (true) {

            RespoProm result = restTemplate.getForObject(url, RespoProm.class);
            RespoProm result2 = restTemplate.getForObject(url1, RespoProm.class);

            if (result.getData().getResult().length >0 && result2.getData().getResult().length >0 ) {
                System.out.println("RICHIESRE AL SECONDO  "+result.getData().getResult()[0].getValue()[1]);
                System.out.println("TEMPI DI RISPOSTA  "+result2.getData().getResult()[0].getValue()[1]);

                String richiestesecondo = result.getData().getResult()[0].getValue()[1];
                String tempirisposta = result2.getData().getResult()[0].getValue()[1];

                System.out.println("RICHIESRE AL SECONDO"+richiestesecondo + "    " +"TEMPI DI RISPOSTA"+ tempirisposta);

                kafkaTemplate.send(kafkaSpoutTopic, "rs|" + richiestesecondo);
                kafkaTemplate.send(kafkaSpoutTopic, "tr|" + tempirisposta);

                System.out.println("DOPO KAFKA     RICHIESRE AL SECONDO  "+richiestesecondo + "    " +"TEMPI DI RISPOSTA  "+ tempirisposta);
            } else {
                System.out.println("no data");
            }

            TimeUnit.SECONDS.sleep(10);
        }
    }


}
