package com.example.cinehub.exception.jsonMessages.errorResponse;

import com.example.cinehub.exception.ApiException;
import com.example.cinehub.exception.jsonMessages.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponseDTO {

    private HttpStatus httpStatus;
    private final List<ErrorDTO> errors = new ArrayList<>();

    private ErrorResponseDTO() {
        httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
    }

    public ErrorResponseDTO(ErrorDTO error) {
        this();
        this.errors.add(error);
    }

    public ErrorResponseDTO(ErrorMessage error) {
        this();
        setHttpStatus(error.getErrCode());
        this.errors.add(new ErrorDTO(error.getErrCode(), error.getErrMsg()));
    }

    public ErrorResponseDTO(ApiException error) {
        this(error.getErrorMessage());
    }

    public ErrorResponseDTO(Errors error) {
        this(error.getAllErrors());
    }

    public ErrorResponseDTO(List<ObjectError> errors) {
        this(errors, 400);
    }

    public ErrorResponseDTO(List<ObjectError> errors, int defaultCode) {
        this();
        errors.forEach(error -> this.errors.add(new ErrorDTO(defaultCode, "Property " + ((FieldError) error).getField() + " " + error.getDefaultMessage() + "!")));
    }


    public ErrorResponseDTO addError(ErrorDTO errorDTO) {
        this.errors.add(errorDTO);
        return this;
    }

    public ErrorResponseDTO setHttpStatus(int status) {
        HttpStatus resolvedHttpStatus = HttpStatus.resolve(status);
        this.httpStatus = resolvedHttpStatus == null ? HttpStatus.UNPROCESSABLE_ENTITY : resolvedHttpStatus;
        return this;
    }

    public ResponseEntity<ErrorResponseJson<List<ErrorDTO>>> getResponse() {
        return new ResponseEntity<>(
                new ErrorResponseJson<>(errors),
                httpStatus);
    }
}
