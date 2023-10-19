package com.example.cinehub.exception.jsonMessages.errorResponse;


import com.example.cinehub.exception.ApiException;
import com.example.cinehub.exception.jsonMessages.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {

    private HttpStatus httpStatus;
    private final List<ErrorMessage> errors = new ArrayList<>();

    private ErrorResponse() {
        httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
    }

    public ErrorResponse(ErrorMessage error) {
        this();
        setHttpStatus(error.getErrCode());
        this.errors.add(error);
    }

    public ErrorResponse(ApiException error) {
        this(error.getErrorMessage());
    }

    public ErrorResponse(Errors error) {
        this(error.getAllErrors());
    }

    public ErrorResponse(List<ObjectError> errors) {
        this(errors, 400);
    }

    public ErrorResponse(List<ObjectError> errors, int defaultCode) {
        this();
        errors.forEach(error -> this.errors.add(new ErrorMessage(defaultCode, error.getDefaultMessage())));
    }

    public ErrorResponse addError(ErrorMessage errorDTO) {
        this.errors.add(errorDTO);
        return this;
    }

    public ErrorResponse setHttpStatus(int status) {
        HttpStatus resolvedHttpStatus = HttpStatus.resolve(status);
        this.httpStatus = resolvedHttpStatus == null ? HttpStatus.UNPROCESSABLE_ENTITY : resolvedHttpStatus;
        return this;
    }

    public ResponseEntity<ErrorResponseBody<List<ErrorMessage>>> getResponse() {
        return new ResponseEntity<>(
                new ErrorResponseBody<>(errors),
                httpStatus);
    }
}
