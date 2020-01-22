package com.dslab.spout;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.Map;

public class result {
    @JsonProperty("metric")
    private metric metric;
    @JsonProperty("value")
    private String[] value;

    public com.dslab.spout.metric getMetric() {
        return metric;
    }

    public void setMetric(com.dslab.spout.metric metric) {
        this.metric = metric;
    }

    public String[] getValue() {
        return value;
    }

    public void setValue(String[] value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "result{" +
                "metric=" + metric +
                ", value=" + Arrays.toString(value) +
                '}';
    }
}
