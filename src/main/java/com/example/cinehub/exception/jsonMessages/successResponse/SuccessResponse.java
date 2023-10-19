package com.example.cinehub.exception.jsonMessages.successResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SuccessResponse<T> {

    private HttpStatus httpStatus;
    private T data;

    public SuccessResponse(T data) {
        httpStatus = HttpStatus.OK;
        this.data = data;
    }

    public SuccessResponse<T> setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    public SuccessResponse<T> setData(T data) {
        this.data = data;
        return this;
    }

    public ResponseEntity<SuccessResponseBody<T>> getResponse() {
        return new ResponseEntity<>(
                new SuccessResponseBody<>(data),
                httpStatus);
    }

}