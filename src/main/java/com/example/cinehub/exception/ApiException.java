package com.example.cinehub.exception;


import com.example.cinehub.exception.jsonMessages.ErrorMessage;
import lombok.Getter;

@Getter
public class ApiException extends Exception {
    private final ErrorMessage errorMessage;

    public ApiException(ErrorMessage errorMessage) {
        super(errorMessage.getErrMsg());
        this.errorMessage = errorMessage;
    }

}
