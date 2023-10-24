package com.example.cinehub.config;

import com.example.cinehub.exception.jsonMessages.ErrorMessages;
import com.example.cinehub.exception.jsonMessages.errorResponse.ErrorResponse;
import com.example.cinehub.exception.jsonMessages.errorResponse.ErrorResponseDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ControllerAdvice
public class GlobalExceptionHandler {

    //Handles exception caused by wrong JSON structure in the requests from all controllers.
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(Exception e) {
        return new ErrorResponse(ErrorMessages.INVALID_JSON_STRUCTURE).getResponse();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e) {

        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();

        List<ObjectError> errors = new ArrayList<>();

        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            String message = constraintViolation.getMessage();
            errors.add( new ObjectError(e.getClass().getSimpleName(),message));
        }

        return new ErrorResponseDTO(errors).getResponse();
    }

}
