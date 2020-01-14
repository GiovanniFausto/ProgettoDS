package com.dslab.apigateway;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudConfig {
    @Value("${app.vms.addr}") //per impostarlo dall'application.properties
    public String addr_vms;


    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder,Filtro filtro) {
        Filtro.Config config= new Filtro.Config();

       return builder.routes()
                .route(r -> r.path("/vms/**")
                        .filters(f->f.filter(filtro.apply(config)))
                        .uri("http://"+addr_vms+":8080/vms")
                        .id("videoManagementService"))
                .build();
    }
}
