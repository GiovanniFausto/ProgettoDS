package com.dslab.videomanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class VideomanagementserviceApplication {
    public static void main(String[] args) throws UnknownHostException {

        SpringApplication.run(VideomanagementserviceApplication.class, args);
        // Local address
        System.out.println("PARTITO VMS \n\n\n\n\n\n\n");
        System.out.println(InetAddress.getLocalHost().getHostAddress()+
        InetAddress.getLocalHost().getHostName()+

        // Remote address
        InetAddress.getLoopbackAddress().getHostAddress()+
        InetAddress.getLoopbackAddress().getHostName());
    }

}
