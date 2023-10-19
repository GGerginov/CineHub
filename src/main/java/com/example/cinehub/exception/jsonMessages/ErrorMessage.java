package com.example.cinehub.exception.jsonMessages;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class ErrorMessage {

    @JsonProperty
    private int errCode;

    @JsonProperty
    private String errMsg;

    public ErrorMessage(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @JsonGetter
    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    @JsonGetter
    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    @Override
    public String toString() {
        return "{" + "\n" +
                "\"errCode\": " + errCode + ",\n" +
                "\"errMsg\": \"" + errMsg + "\"\n" +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorMessage that = (ErrorMessage) o;
        return errCode == that.errCode &&
                Objects.equals(errMsg, that.errMsg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errCode, errMsg);
    }
}
