package com.example.cinehub.exception.jsonMessages.errorResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public class ErrorResponseBody<T> {

    @JsonProperty("success")
    private boolean isSuccess;
    @Getter
    private T errors;

    protected ErrorResponseBody() {
    }

    public ErrorResponseBody(T error) {
        this.isSuccess = false;
        this.errors = error;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

}
