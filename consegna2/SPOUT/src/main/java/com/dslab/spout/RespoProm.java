package com.dslab.spout;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RespoProm {
    @JsonProperty("status")
    private String status;
    @JsonProperty("data")
    private data data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public com.dslab.spout.data getData() {
        return data;
    }

    public void setData(com.dslab.spout.data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RespoProm{" +
                "status='" + status + '\'' +
                ", data=" + data +
                '}';
    }
}
