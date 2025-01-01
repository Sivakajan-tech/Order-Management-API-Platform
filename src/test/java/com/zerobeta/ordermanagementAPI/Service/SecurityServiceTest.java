package com.zerobeta.ordermanagementAPI.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.zerobeta.ordermanagementAPI.Model.Client;
import com.zerobeta.ordermanagementAPI.Repository.ClientRepo;
import com.zerobeta.ordermanagementAPI.Service.Utils.SecurityService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class SecurityServiceTest {
    @InjectMocks
    private SecurityService securityService;

    @Mock
    private ClientRepo clientRepo;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @Test
    void testGetAuthenticatedClient_Success() {
        // Arrange
        String email = "test@example.com";
        Client mockClient = new Client();
        mockClient.setEmail(email);

        UserDetails userDetails = User.withUsername(email).password("password").roles("USER").build();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(clientRepo.findByEmail(email)).thenReturn(Optional.of(mockClient));

        SecurityContextHolder.setContext(securityContext);

        // Act
        Client result = securityService.getAuthenticatedClient();

        // Assert
        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    @Test
    void testGetAuthenticatedClient_UserNotFoundInRepo() {
        // Arrange
        String email = "test@example.com";

        UserDetails userDetails = User.withUsername(email).password("password").roles("USER").build();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(clientRepo.findByEmail(email)).thenReturn(Optional.empty());

        SecurityContextHolder.setContext(securityContext);

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> securityService.getAuthenticatedClient());
    }

    @Test
    void testGetAuthenticatedClient_PrincipalNotUserDetails() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn("anonymousUser");

        SecurityContextHolder.setContext(securityContext);

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> securityService.getAuthenticatedClient());
    }
}
