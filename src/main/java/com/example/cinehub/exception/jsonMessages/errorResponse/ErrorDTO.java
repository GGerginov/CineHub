package com.example.cinehub.exception.jsonMessages.errorResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDTO {

    private int code;
    private String message;

    public ErrorDTO(String message) {
        this.code = 400;
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