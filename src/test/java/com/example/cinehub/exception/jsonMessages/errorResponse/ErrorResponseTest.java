package com.example.cinehub.exception.jsonMessages.errorResponse;

import com.example.cinehub.exception.ApiException;
import com.example.cinehub.exception.jsonMessages.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {

    @Test
    void testErrorResponseWithSingleErrorMessage() {
        ErrorMessage errorMessage = new ErrorMessage(400, "Bad Request");
        ErrorResponse errorResponse = new ErrorResponse(errorMessage);

        assertEquals(HttpStatus.BAD_REQUEST, errorResponse.getHttpStatus());
        assertEquals(1, errorResponse.getErrors().size());
        assertEquals(errorMessage, errorResponse.getErrors().get(0));
    }

    @Test
    void testErrorResponseWithApiException() {
        ApiException apiException = new ApiException(new ErrorMessage(404, "Not Found"));
        ErrorResponse errorResponse = new ErrorResponse(apiException);

        assertEquals(HttpStatus.NOT_FOUND, errorResponse.getHttpStatus());
        assertEquals(1, errorResponse.getErrors().size());
        assertEquals(apiException.getErrorMessage(), errorResponse.getErrors().get(0));
    }

    @Test
    void testErrorResponseWithObjectErrors() {
        List<ObjectError> objectErrors = Arrays.asList(
                new ObjectError("object1", "Error 1"),
                new ObjectError("object2", "Error 2")
        );
        ErrorResponse errorResponse = new ErrorResponse(objectErrors);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, errorResponse.getHttpStatus());
        assertEquals(2, errorResponse.getErrors().size());
        assertEquals(422, errorResponse.getErrors().get(0).getErrCode());
        assertEquals("Error 1", errorResponse.getErrors().get(0).getErrMsg());
        assertEquals("Error 2", errorResponse.getErrors().get(1).getErrMsg());
    }

    @Test
    void testErrorResponseAddError() {
        ErrorMessage errorMessage1 = new ErrorMessage(500, "Internal Server Error");
        ErrorMessage errorMessage2 = new ErrorMessage(401, "Unauthorized");

        ErrorResponse errorResponse = new ErrorResponse(errorMessage1).addError(errorMessage2);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, errorResponse.getHttpStatus());
        assertEquals(2, errorResponse.getErrors().size());
        assertTrue(errorResponse.getErrors().contains(errorMessage1));
        assertTrue(errorResponse.getErrors().contains(errorMessage2));
    }

    @Test
    void testErrorResponseSetHttpStatus() {
        ErrorMessage errorMessage = new ErrorMessage(0, "Demo message");

        ErrorResponse errorResponse = new ErrorResponse(errorMessage).setHttpStatus(403);

        assertEquals(HttpStatus.FORBIDDEN, errorResponse.getHttpStatus());
    }

}
