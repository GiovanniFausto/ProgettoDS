package com.dslab.videoprocessingservice.controller;

import com.dslab.videoprocessingservice.service.ProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.dslab.videoprocessingservice.video;
@Controller
@RequestMapping(path = "/vps/videos/process")
public class VideoController {

    @Autowired
    ProcessingService processingService;

    @RequestMapping(value = "/", method = RequestMethod.POST) //prende il video id
    public ResponseEntity<video> processavideo(@RequestBody video v) {
        try {
            System.out.println(v.getVideoId());
            processingService.processaVideo(v.getVideoId());;
            return new ResponseEntity<>(v, HttpStatus.OK);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new ResponseEntity<>(v, HttpStatus.BAD_REQUEST);
        }
    }
}

