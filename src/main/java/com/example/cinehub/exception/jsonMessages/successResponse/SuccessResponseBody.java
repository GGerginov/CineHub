package com.example.cinehub.exception.jsonMessages.successResponse;


public class SuccessResponseBody<T> {

    private boolean isSuccess;
    private T data;

    protected SuccessResponseBody() {
    }

    public SuccessResponseBody(T data) {
        this.isSuccess = true;
        this.data = data;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public T getData() {
        return data;
    }

}
