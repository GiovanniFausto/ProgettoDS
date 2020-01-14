package com.dslab.videomanagementservice.kafka;

import com.dslab.videomanagementservice.entity.Video;
import com.dslab.videomanagementservice.service.VideoManagementService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class listener {
    @Value("${app.upload.dir}") //per impostarlo dall'application.properties
    public String uploadDir;
    @Autowired
    VideoManagementService videoManagementService;

    @Value(value = "${KAFKA_MAIN_TOPIC}")
    private String mainTopic;

    @KafkaListener(topics="${KAFKA_MAIN_TOPIC}")
    public void listen(String message) throws IOException {
        System.out.println("Received message " + message);

        String[] messageParts = message.split("\\|");

        if (messageParts[0].equals("processed")) {
            String id = messageParts[1];
            Video v=videoManagementService.getVideo(Integer.parseInt(id)).get();
            v.setStato("Available");
            videoManagementService.addVideo(v);
        }
        if (messageParts[0].equals("processingFailed")) {
            String id = messageParts[1];
            Video v=videoManagementService.getVideo(Integer.parseInt(id)).get();
            v.setStato("NotAvailable");
            videoManagementService.addVideo(v);
            FileUtils.deleteDirectory(new File(uploadDir+id));

        }
    }
}
