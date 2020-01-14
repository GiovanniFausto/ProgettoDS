package com.dslab.apigateway;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.Gauge;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.concurrent.TimeUnit;

@Component
public class Filtro extends AbstractGatewayFilterFactory<Filtro.Config> {

    public float count;
    RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
    CompositeMeterRegistry meterRegistry=new CompositeMeterRegistry();
    /*final static Gauge apiRequest= Gauge.build().name("apirichiesta").help("metodo e uri api richiesta.").register();
    final static Gauge payloadsizein = Gauge.build().name("paysizein").help("payload size input.").register();
    final static Gauge tempisRisposta = Gauge.build().name("temrrisp").help("tempi risposta.").register();
    final static Gauge richiesteSecondogaug= Gauge.build().name("richiestesecondo").help("richieste al secondo.").register();*/

    public Filtro(){
         super(Config.class);
         this.count=0;
         this.meterRegistry.add(new PrometheusMeterRegistry(null));
    }



    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            long start = System.currentTimeMillis(); //prende il tempo corrente im ms
            float uptime = TimeUnit.MILLISECONDS.toSeconds(rb.getUptime());//prende i secondi in run
            Route r=exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);//serve per prendere l'uri
            String uri=r.getUri().toString(); // URI
            String metodo=exchange.getRequest().getMethod().toString(); //metodo ges posr ecc
            long size=exchange.getRequest().getHeaders().getContentLength(); //size input
            String statorichiesta=exchange.getResponse().getStatusCode().toString();
            count++; //rischieste totali
            float richiesteSecondo=count/uptime;
            System.out.println(count+""+uptime);
            MeterRegistry mr= meterRegistry;

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                long temporisposta = System.currentTimeMillis() - start; //tempi di risposta
                String statoresponse=exchange.getResponse().getStatusCode().toString();//200 errori vari ecc
                //apiRequest.labels(metodo+  "  " + uri);
                mr.gauge("apirichiesta",metodo+" "+uri,s ->0);
                mr.gauge("paysizein",size);
                mr.gauge("temrrisp",temporisposta);
                mr.gauge("richiestesecondo",richiesteSecondo);

                //apiRequest.inc();
                /*payloadsizein.set(size);
                tempisRisposta.set(temporisposta);
                richiesteSecondogaug.set(richiesteSecondo);*/

                System.out.println("METODO\t:"+metodo);
                System.out.println("URI\t:"+uri);
                System.out.println("SIZE INPUT\t:"+size);
                System.out.println("TEMPO DI RISPOSTA\t:"+temporisposta);
                System.out.println("STATO RICHIESTA\t:"+statorichiesta);
                System.out.println("STATO RISPOSTA\t:"+statoresponse);// da controllare errori
                System.out.println("RICHIESTE AL SECONDO\t:"+richiesteSecondo);
            }));
        };
    }

    public static class Config{

    }

}
