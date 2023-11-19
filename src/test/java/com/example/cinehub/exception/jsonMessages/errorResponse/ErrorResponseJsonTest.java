package com.example.cinehub.exception.jsonMessages.errorResponse;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseJsonTest {

    @Test
    void testErrorResponseJsonWithSingleError() {
        String errorMessage = "An error occurred";
        ErrorResponseJson<String> errorResponseJson = new ErrorResponseJson<>(errorMessage);

        assertFalse(errorResponseJson.isSuccess());
        assertEquals(errorMessage, errorResponseJson.getErrors());
    }

    @Test
    void testErrorResponseJsonWithMultipleErrors() {

        ErrorDTO error1 = new ErrorDTO(400, "Bad Request");
        ErrorDTO error2 = new ErrorDTO(404, "Not Found");
        ErrorResponseJson<ErrorDTO[]> errorResponseJson = new ErrorResponseJson<>(new ErrorDTO[]{error1, error2});

        assertFalse(errorResponseJson.isSuccess());
        assertArrayEquals(new ErrorDTO[]{error1, error2}, errorResponseJson.getErrors());
    }

}
