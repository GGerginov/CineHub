package com.example.cinehub.exception.jsonMessages.errorResponse;

import com.example.cinehub.exception.ApiException;
import com.example.cinehub.exception.jsonMessages.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseDTOTest {

    @Test
    void testErrorResponseWithSingleErrorDTO() {
        ErrorDTO errorDTO = new ErrorDTO(400, "Bad Request");
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(errorDTO);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, errorResponseDTO.getHttpStatus());
        assertEquals(1, errorResponseDTO.getErrors().size());
        assertEquals(errorDTO, errorResponseDTO.getErrors().get(0));
    }

    @Test
    void testErrorResponseWithErrorMessage() {
        ErrorMessage errorMessage = new ErrorMessage(404, "Not Found");
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(errorMessage);

        assertEquals(HttpStatus.NOT_FOUND, errorResponseDTO.getHttpStatus());
        assertEquals(1, errorResponseDTO.getErrors().size());
        ErrorDTO errorDTO = errorResponseDTO.getErrors().get(0);
        assertEquals(404,errorDTO.getCode());
        assertEquals("Not Found",errorDTO.getMessage());
    }

    @Test
    void testErrorResponseWithApiException() {
        ApiException apiException = new ApiException(new ErrorMessage(500, "Internal Server Error"));
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(apiException);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, errorResponseDTO.getHttpStatus());
        assertEquals(1, errorResponseDTO.getErrors().size());
        ErrorDTO errorDTO = errorResponseDTO.getErrors().get(0);
        assertEquals(500,errorDTO.getCode());
        assertEquals("Internal Server Error",errorDTO.getMessage());
    }

    @Test
    void testErrorResponseWithObjectErrors() {
        List<ObjectError> objectErrors = Arrays.asList(
                new FieldError("object1", "field1", "Error 1"),
                new FieldError("object2", "field2", "Error 2")
        );
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(objectErrors);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, errorResponseDTO.getHttpStatus());
        assertEquals(2, errorResponseDTO.getErrors().size());

        ErrorDTO errorDTO1 = errorResponseDTO.getErrors().get(0);

        assertEquals(400,errorDTO1.getCode());
        assertEquals("Property field1 Error 1!",errorDTO1.getMessage());

        ErrorDTO errorDTO2 = errorResponseDTO.getErrors().get(1);
        assertEquals(400,errorDTO2.getCode());
        assertEquals("Property field2 Error 2!",errorDTO2.getMessage());
    }

    @Test
    void testErrorResponseAddError() {
        ErrorDTO errorDTO1 = new ErrorDTO(500, "Internal Server Error");
        ErrorDTO errorDTO2 = new ErrorDTO(401, "Unauthorized");

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(errorDTO1).addError(errorDTO2);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, errorResponseDTO.getHttpStatus());
        assertEquals(2, errorResponseDTO.getErrors().size());
        assertTrue(errorResponseDTO.getErrors().contains(errorDTO1));
        assertTrue(errorResponseDTO.getErrors().contains(errorDTO2));
    }

    @Test
    void testErrorResponseSetHttpStatus() {

        ErrorDTO errorDTO = new ErrorDTO(500, "Test message");
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(errorDTO).setHttpStatus(403);

        assertEquals(HttpStatus.FORBIDDEN, errorResponseDTO.getHttpStatus());
    }
}
