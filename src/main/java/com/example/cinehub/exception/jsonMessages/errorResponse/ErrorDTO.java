package com.example.cinehub.exception.jsonMessages.errorResponse;

public class ErrorDTO {

    private int code;
    private String message;

    public ErrorDTO(String message) {
        this.code = 400;
        this.message = message;
    }

    public ErrorDTO(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\"code\": " + code + ",\n" +
                "\"message\": \"" + message + "\"\n" +
                '}';
    }
}