package com.dslab.videoprocessingservice.service;

import org.springframework.stereotype.Service;
import  com.dslab.videoprocessingservice.wThread;
import java.util.ArrayList;


@Service
public class ProcessingService {//fa i thread per i process
    public void processaVideo(String id) throws InterruptedException {
        ArrayList<Thread> t=new ArrayList<>();
        t.add(new Thread(new wThread(id)));
        t.get(t.size()-1).start();//prende l'ultimo inserito e lo fa partire
        t.get(t.size()-1).join();
        System.out.println(id);
        for(int j=0;j<t.size();j++){
            if(!t.get(j).isAlive()){
                t.remove(t.get(j));
            }
        }
    }

}
