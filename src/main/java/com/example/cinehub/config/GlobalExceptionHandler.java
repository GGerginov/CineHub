package com.example.cinehub.config;

import com.example.cinehub.exception.jsonMessages.ErrorMessages;
import com.example.cinehub.exception.jsonMessages.errorResponse.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    //Handles exception caused by wrong JSON structure in the requests from all controllers.
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(Exception e) {
        return new ErrorResponse(ErrorMessages.INVALID_JSON_STRUCTURE).getResponse();
    }

}
