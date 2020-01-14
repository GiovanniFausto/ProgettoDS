package com.dslab.spout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.RestTemplate;


import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class SpoutApplication {


    public static void main(String[] args) throws InterruptedException {

        SpringApplication.run(SpoutApplication.class, args);
        Stream stream= new Stream();
        while (true) {
            stream.stream();
            TimeUnit.SECONDS.sleep(10);
        }

    }

}
