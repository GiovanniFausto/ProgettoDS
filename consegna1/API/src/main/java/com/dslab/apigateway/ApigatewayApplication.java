package com.dslab.apigateway;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;
import java.net.UnknownHostException;


@SpringBootApplication

public class ApigatewayApplication {




    public static void main(String[] args) throws UnknownHostException {
       // MeterRegistry meterRegistry = null;
        //Counter getCounter;
        SpringApplication.run(ApigatewayApplication.class, args);
        System.out.println("PARTITO API \n\n\n\n\n\n\n\n\n");
        System.out.println(InetAddress.getLocalHost().getHostAddress()+
                InetAddress.getLocalHost().getHostName()+

                // Remote address
                InetAddress.getLoopbackAddress().getHostAddress()+
                InetAddress.getLoopbackAddress().getHostName());

        //getCounter= Counter.builder("get.counter").tag("type","get").description("contatore di get").register(meterRegistry);

    }



}
