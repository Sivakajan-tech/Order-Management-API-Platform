package com.zerobeta.ordermanagementAPI.Service;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import com.zerobeta.ordermanagementAPI.Service.Utils.JWTService;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class JWTServiceTest {
    private JWTService jwtService;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtService = new JWTService();
    }

    @Test
    void generateToken_ShouldReturnNonNullToken() {
        // Arrange
        String username = "testUser";

        // Act
        String token = jwtService.generateToken(username);

        // Assert
        assertNotNull(token);
    }

    @Test
    void extractUserName_ShouldReturnCorrectUsername() {
        // Arrange
        String username = "testUser";
        String token = jwtService.generateToken(username);

        // Act
        String extractedUsername = jwtService.extractUserName(token);

        // Assert
        assertEquals(username, extractedUsername);
    }

    @Test
    void validateToken_ShouldReturnTrueIfValid() {
        // Arrange
        String username = "testUser";
        String token = jwtService.generateToken(username);
        when(userDetails.getUsername()).thenReturn(username);

        // Act
        boolean isValid = jwtService.validateToken(token, userDetails);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void validateToken_ShouldReturnFalseIfUserNameMismatch() {
        // Arrange
        String username = "testUser";
        String token = jwtService.generateToken(username);
        when(userDetails.getUsername()).thenReturn("differentUser");

        // Act
        boolean isValid = jwtService.validateToken(token, userDetails);

        // Assert
        assertFalse(isValid);
    }

    @Test
    void generateSecretKey_ShouldReturnBase64EncodedString() {
        // Arrange & Act
        String secretKey = jwtService.generateSecretKey();

        // Assert
        assertThat(secretKey).matches("[A-Za-z0-9+/=]+");
    }

}
