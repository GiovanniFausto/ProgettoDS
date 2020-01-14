package com.dslab.videomanagementservice.service;

import com.dslab.videomanagementservice.entity.*;
import com.dslab.videomanagementservice.exceptions.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class VideoManagementService {

    @Autowired
    VideoRepository videoRepository;

    @Value("${app.upload.dir}") //per impostarlo dall'application.properties
    public String uploadDir; //percorso della directory in cui caricare il video

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public void uploadVideo(MultipartFile video,Integer id){//carica il video nella cartella video
        try{
            new File(uploadDir+id).mkdirs();//creo la cartella con id  //video.getOriginalFilename()
            Path copyLocation = Paths.get(uploadDir+id + File.separator + StringUtils.cleanPath("video.mp4"));
            Files.copy(video.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);

        } catch (Exception e) {
            e.printStackTrace();
            throw new VideoStorageException("Could not store file " + video.getOriginalFilename() + ". Please try again!");
        }
    }


    public Video addVideo(Video video){
        return videoRepository.save(video);
    }
    public Iterable<Video> getAllVideo(){
        return videoRepository.findAll();
    }
    public Optional<Video> getVideo(Integer id){
        return videoRepository.findById(id);
    }

}
