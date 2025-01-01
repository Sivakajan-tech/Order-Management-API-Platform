package com.zerobeta.ordermanagementAPI.Controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.zerobeta.ordermanagementAPI.DTO.LoginRequestDTO;
import com.zerobeta.ordermanagementAPI.DTO.RegisterRequestDTO;
import com.zerobeta.ordermanagementAPI.Fixture.ClientFixture;
import com.zerobeta.ordermanagementAPI.Fixture.DTOFixture;
import com.zerobeta.ordermanagementAPI.Model.Client;
import com.zerobeta.ordermanagementAPI.Service.ClientService;
import com.zerobeta.ordermanagementAPI.Service.Utils.JWTService;

@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {

    @InjectMocks
    private ClientController clientController;

    @Mock
    private ClientService clientService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTService jwtService;

    @Test
    void testGetAllClients() {
        // Arrange
        Client client1 = ClientFixture.createClient("1");
        Client client2 = ClientFixture.createClient("2");
        when(clientService.getAllClients()).thenReturn(Arrays.asList(client1, client2));

        // Act
        List<Client> clients = clientController.getAllClients();

        // Assert
        assertEquals(2, clients.size());
        assertEquals("1first_name", clients.get(0).getFirst_name());
        verify(clientService, times(1)).getAllClients();

    }

    @Test
    void testFindById_Success() {
        // Arrange
        UUID clientId = UUID.randomUUID();
        Client client = new Client(clientId, "John", "Doe", "john.doe@example.com", "password");
        when(clientService.getClient(clientId)).thenReturn(client);

        // Act
        ResponseEntity<Client> response = clientController.findById(clientId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(client, response.getBody());
        assertEquals(clientId, response.getBody().getId());
        verify(clientService, times(1)).getClient(clientId);
    }

    @Test
    void testFindById_NotFound() {
        // Arrange
        UUID clientId = UUID.randomUUID();
        when(clientService.getClient(clientId)).thenReturn(null);

        // Act
        ResponseEntity<Client> response = clientController.findById(clientId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(clientService, times(1)).getClient(clientId);
    }

    @Test
    void testRegisterClient() {
        // Arrange
        RegisterRequestDTO registerRequest = DTOFixture.createRegisterRequestDTO();
        Client client = Client.fromRegisterRequestDTO(registerRequest);
        when(clientService.addClient(any(Client.class))).thenReturn(client);

        // Act
        ResponseEntity<Client> response = clientController.registerClient(registerRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(client, response.getBody());
        assertNotNull(response.getBody());
        verify(clientService, times(1)).addClient(any(Client.class));
    }

    @Test
    void testLogin_Success() {
        // Arrange
        LoginRequestDTO loginRequest = DTOFixture.createLoginRequestDTO();
        String token = "mock-jwt-token";

        when(jwtService.generateToken(loginRequest.getEmail())).thenReturn(token);

        // Act
        ResponseEntity<String> response = clientController.login(loginRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(token, response.getBody());
    }

    @Test
    void testLogin_Failure() {
        // Arrange
        LoginRequestDTO loginRequest = DTOFixture.createLoginRequestDTO();

        doThrow(new RuntimeException("Authentication failed")).when(authenticationManager).authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> clientController.login(loginRequest));
        verify(authenticationManager, times(1)).authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        verifyNoInteractions(jwtService);
    }
}
