package com.zerobeta.ordermanagementAPI.Exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Map;

import org.hibernate.PropertyValueException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

    @Test
    public void testHandleDataIntegrityViolationException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        // Mock the exception
        DataIntegrityViolationException ex = mock(
                DataIntegrityViolationException.class);

        // Call the handler method
        ResponseEntity<Map<String, String>> response = handler.handleDataIntegrityViolationException(ex);

        // Verify the response
        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        Map<String, String> errorDetails = response.getBody();
        assertNotNull(errorDetails);
        assertEquals("Data integrity violation", errorDetails.get("error"));
        assertEquals(ex.getMessage(), errorDetails.get("message"));
    }

    @Test
    public void testHandleJwtException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        // Mock the exception
        JwtException ex = mock(JwtException.class);

        // Call the handler method
        ResponseEntity<Map<String, String>> response = handler.handleJwtException(ex);

        // Verify the response
        assertNotNull(response);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        Map<String, String> errorDetails = response.getBody();
        assertNotNull(errorDetails);
        assertEquals("JWT parsing failed", errorDetails.get("error"));
        assertEquals("Please provide a valid token.", errorDetails.get("message"));
    }

    @Test
    public void testHandleSignatureException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        // Mock the exception
        SignatureException ex = mock(SignatureException.class);

        // Call the handler method
        ResponseEntity<Map<String, String>> response = handler.handleSignatureException(ex);

        // Verify the response
        assertNotNull(response);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        Map<String, String> errorDetails = response.getBody();
        assertNotNull(errorDetails);
        assertEquals("Invalid JWT signature", errorDetails.get("error"));
        assertEquals("Please provide a valid token.", errorDetails.get("message"));
    }

    @Test
    public void testHandleBadCredentials() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        // Mock the exception
        BadCredentialsException ex = mock(BadCredentialsException.class);

        // Call the handler method
        ResponseEntity<Map<String, String>> response = handler.handleBadCredentials(ex);

        // Verify the response
        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        Map<String, String> errorDetails = response.getBody();
        assertNotNull(errorDetails);
        assertEquals("Invalid credentials", errorDetails.get("error"));
        assertEquals(ex.getMessage(), errorDetails.get("message"));
    }

    @Test
    public void testHandleGeneralException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        // Mock the exception
        Exception ex = mock(Exception.class);

        // Call the handler method
        ResponseEntity<Map<String, String>> response = handler.handleGeneralException(ex);

        // Verify the response
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Map<String, String> errorDetails = response.getBody();
        assertNotNull(errorDetails);
        assertEquals("An unexpected error occurred.", errorDetails.get("error"));
        assertEquals(ex.getMessage(), errorDetails.get("message"));
    }

    @Test
    public void testHandlePropertyValueException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        // Mock the exception
        PropertyValueException ex = mock(PropertyValueException.class);

        // Call the handler method
        ResponseEntity<Map<String, String>> response = handler.handlePropertyValueException(ex);

        // Verify the response
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, String> errorDetails = response.getBody();
        assertNotNull(errorDetails);
        assertEquals("Missing required field", errorDetails.get("error"));
        assertEquals(ex.getPropertyName(), errorDetails.get("field"));
        assertEquals(ex.getEntityName(), errorDetails.get("entity"));
    }

    @Test
    public void testHandleSQLIntegrityConstraintViolation() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        // Mock the exception
        SQLIntegrityConstraintViolationException ex = mock(SQLIntegrityConstraintViolationException.class);

        // Call the handler method
        ResponseEntity<Map<String, String>> response = handler.handleSQLIntegrityConstraintViolation(ex);

        // Verify the response
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        Map<String, String> errorDetails = response.getBody();
        assertNotNull(errorDetails);
        assertEquals("Duplicate entry detected", errorDetails.get("error"));
        assertEquals(ex.getMessage(), errorDetails.get("message"));
    }
}
