package com.example.cinehub.exception.jsonMessages.errorResponse;


import com.fasterxml.jackson.annotation.JsonProperty;


public class ErrorResponseJson<T> {

    @JsonProperty("success")
    private boolean isSuccess;
    private T errors;

    protected ErrorResponseJson() {
    }

    public ErrorResponseJson(T error) {
        this.isSuccess = false;
        this.errors = error;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public T getErrors() {
        return errors;
    }

}
