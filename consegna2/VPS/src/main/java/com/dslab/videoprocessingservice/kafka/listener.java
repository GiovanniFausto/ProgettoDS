package com.dslab.videoprocessingservice.kafka;

import com.dslab.videoprocessingservice.service.ProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class listener {
    @Autowired
    ProcessingService processingService;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value(value = "${KAFKA_MAIN_TOPIC}")
    private String mainTopic;

    @KafkaListener(topics="${KAFKA_MAIN_TOPIC}")
    public void listen(String message) {
        System.out.println("Received message " + message);

        String[] messageParts = message.split("\\|");

        if (messageParts[0].equals("process")) {
            String id = messageParts[1];
            try {
                processingService.processaVideo(id);
                kafkaTemplate.send(mainTopic,"processed|"+id);
            } catch (InterruptedException e) {
                kafkaTemplate.send(mainTopic,"processingFailed|"+id);
                e.printStackTrace();
            }
        }
    }
}
