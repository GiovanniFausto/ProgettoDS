package com.dslab.spout;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public class data {
    @JsonProperty("resultType")
    private String resultType;
    @JsonProperty("result")
    private result[] result;

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public com.dslab.spout.result[] getResult() {
        return result;
    }

    public void setResult(com.dslab.spout.result[] result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "data{" +
                "resultType='" + resultType + '\'' +
                ", result=" + Arrays.toString(result) +
                '}';
    }
}
