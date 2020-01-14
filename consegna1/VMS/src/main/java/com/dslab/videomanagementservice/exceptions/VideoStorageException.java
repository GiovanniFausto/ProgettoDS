package com.dslab.videomanagementservice.exceptions;

public class VideoStorageException  extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private String msg;

    public VideoStorageException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
