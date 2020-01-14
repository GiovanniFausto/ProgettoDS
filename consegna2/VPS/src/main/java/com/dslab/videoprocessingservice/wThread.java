package com.dslab.videoprocessingservice;

import java.io.IOException;


public class wThread implements Runnable {
    private  String id;

    public wThread(String id){
        this.id=id;

    }

    @Override
    public void run() {//thread che fa partire lo script
        String cmd[]={"/bin/bash","-c","/vps/script.sh " +id};
        //String cmd[]={"CMD","[\"/bin/bash/\", /home/giovanni/Scrivania/SistemiDistribuiti/ds/VPS/src/main/java/com/dslab/videoprocessingservice/script.sh " +id+"]"};
        ProcessBuilder processBuilder=new ProcessBuilder(cmd);
        try {
            Process process=processBuilder.start();
        } catch (IOException  e) {
            e.printStackTrace();
        }
    }
}

