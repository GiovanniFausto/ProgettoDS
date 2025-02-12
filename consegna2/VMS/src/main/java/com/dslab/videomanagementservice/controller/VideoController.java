package com.dslab.videomanagementservice.controller;

import com.dslab.videomanagementservice.entity.*;
import com.dslab.videomanagementservice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;


@Controller
@RequestMapping(path = "/vms/videos")
public class VideoController {
    @Autowired
    VideoManagementService videoManagementService;// per i video
    @Autowired
    VideoServerUserService videoServerUserService;// per l'utente

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Value("${app.api.addr}") //address del vps
    public String addr_api;

    @Value(value = "${KAFKA_MAIN_TOPIC}")
    private String mainTopic;
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //POST http://localhost:8080/vms/videos/ (1)
    @PostMapping(path = "/") // per inserire devo essere loggato
    public @ResponseBody ResponseEntity<Optional> register(Authentication auth, @RequestBody Video video) {// carico {nomevideo,autorevideo}
            String email = auth.getName();
            User user = videoServerUserService.getByEmail(email);
            video.setUser(user);
            video.setStato("WaitingUpload");
            videoManagementService.addVideo(video);
            return new ResponseEntity(video,HttpStatus.OK);
    }

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //POST http://localhost:8080/vms/videos/{id} (2)
    @PostMapping(path= "/{id}") //prende il file mp4, poi manda la POST al processing service per elaborare il video
    public @ResponseBody ResponseEntity<Optional> uploadVideo(Authentication authentication, @PathVariable Integer id, @RequestParam("file") MultipartFile video, RedirectAttributes redirectAttributes){
        String email=authentication.getName(); //prende la mail per vedere se è autenticato
        if (videoManagementService.getVideo(id).isPresent()) { //il video esiste nel db ed è dello stesso utente autenticato
            Video videocaricato = videoManagementService.getVideo(id).get();
            if (videocaricato.getStato().equals("WaitingUpload")) {//controlla se è in attesa di up
                if (videocaricato.getUser().getEmail().equals(email)) {//controlla se è lo stesso utente loggato
                    videoManagementService.uploadVideo(video, id);
                    videocaricato.setStato("Uploaded");
                    videoManagementService.addVideo(videocaricato);//serve per mettere lo stato ad upload
                    this.kafkaTemplate.send(mainTopic, "process|" + id);
                    return new ResponseEntity(videocaricato, HttpStatus.OK);
                } else {
                    return new ResponseEntity("NON SEI L'UTENTE GIUSTO PER QUESTO ID", HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity("VIDEO NON WAITUPLOAD", HttpStatus.BAD_REQUEST);
            }
        }
        else {
            return new ResponseEntity("VIDEO NON PRESENTE", HttpStatus.BAD_REQUEST);
        }
    }

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //GET http://localhost:8080/vms/videos/ (3)
    @GetMapping(path= "/") //ritorna tutti i video
    public @ResponseBody Iterable<Video> getAll() {
        return videoManagementService.getAllVideo();
    }

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //GET http://localhost:8080/vms/videos/{id} (4)
    @GetMapping(path= "/{id}") //ritorna un video specifico MPD
    public @ResponseBody ResponseEntity<Optional> getVideo (@PathVariable Integer id, HttpServletResponse response) throws IOException, URISyntaxException {
        if (videoManagementService.getVideo(id).isPresent()){// controlla se il video c'è ed è stato caricato
            Video video = videoManagementService.getVideo(id).get();
            if(video.getStato().equals("Available")) {//controllo se ho fatto upload del video
                return  ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY) //ritorna 301 e l'url di dove è il file
                       .header(HttpHeaders.LOCATION,"http://"+addr_api+":8080/videofiles/"+id+"/video.mpd")// è l'url per l'api
                       .build();
            }
            else {
               return new ResponseEntity("VIDEO ANCORA NON AVSIBLE",HttpStatus.NOT_FOUND);
            }
        }
        else{
           return new ResponseEntity("VIDEO NON PRESENTE",HttpStatus.NOT_FOUND);
        }
    }
}
