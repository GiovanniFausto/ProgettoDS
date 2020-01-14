package com.dslab.apigateway;


import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.Gauge;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
;

@Controller
@RequestMapping(path = "/videofiles")
public class ApiController {

    /*public Gauge payloadsizeout = Gauge.build().name("paysizeout").help("payload size output.").register();*/
    CompositeMeterRegistry meterRegistry=new CompositeMeterRegistry();

    @GetMapping(path= "/{id}/video.mpd")//ritona il file mpd relativo all'id
    public @ResponseBody ResponseEntity<byte[]> getVideo (@PathVariable Integer id) throws IOException {
        String filename= "var/videofiles/"+id+"/video.mpd";
        Path path = Paths.get(filename);
        meterRegistry.add(new PrometheusMeterRegistry(null));
        MeterRegistry mr= meterRegistry;
        byte[] mpdcontents = null;
            try {
                mpdcontents = Files.readAllBytes(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/xml"));
        headers.add("content-disposition", "inline;filename=" + filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        mr.gauge("paysizeout",headers.getContentLength());
        return  new ResponseEntity<byte[]>(mpdcontents,headers,HttpStatus.OK);

    }
}
