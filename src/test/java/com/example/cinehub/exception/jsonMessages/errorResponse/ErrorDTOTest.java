package com.example.cinehub.exception.jsonMessages.errorResponse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ErrorDTOTest {

    @Test
    public void testNoArgsConstructorWhenUsingNoArgsConstructorThenCreateErrorDTOWithDefaultValues() {
        
        ErrorDTO errorDTO = new ErrorDTO("Test");

        
        int actualCode = errorDTO.getCode();
        String actualMessage = errorDTO.getMessage();

        
        assertEquals(400, actualCode);
        assertEquals("Test",actualMessage);
    }

    @Test
    public void testAllArgsConstructorWhenUsingAllArgsConstructorThenCreateErrorDTOWithGivenValues() {
        
        int code = 400;
        String message = "Test Message";
        ErrorDTO errorDTO = new ErrorDTO(code, message);

        
        int actualCode = errorDTO.getCode();
        String actualMessage = errorDTO.getMessage();

        
        assertEquals(code, actualCode);
        assertEquals(message, actualMessage);
    }

    @Test
    public void testGetAndSetCodeWhenCodeIsSetToValidCodeThenReturnCode() {
        
        int code = 400;
        ErrorDTO errorDTO = new ErrorDTO(0, "Test Message");

        errorDTO.setCode(code);
        int actualCode = errorDTO.getCode();

        
        assertEquals(code, actualCode);
    }

    @Test
    public void testGetAndSetMessageWhenMessageIsSetToValidMessageThenReturnMessage() {
        
        String message = "Test Message";
        ErrorDTO errorDTO = new ErrorDTO(400, "Some message");

        errorDTO.setMessage(message);
        String actualMessage = errorDTO.getMessage();

        
        assertEquals(message, actualMessage);
    }

    @Test
    public void testToStringWhenUsingToStringMethodThenReturnCorrectStringRepresentation() {
        
        ErrorDTO errorDTO = new ErrorDTO(400, "Test Message");
        String expectedStringRepresentation = "{\n" +
                "\"code\": 400,\n" +
                "\"message\": \"Test Message\"\n" +
                "}";

        
        String actualStringRepresentation = errorDTO.toString();

        
        assertEquals(expectedStringRepresentation, actualStringRepresentation);
    }
}