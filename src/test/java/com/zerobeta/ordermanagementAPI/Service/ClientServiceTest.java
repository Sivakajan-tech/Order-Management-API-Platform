package com.zerobeta.ordermanagementAPI.Service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.zerobeta.ordermanagementAPI.DTO.RegisterRequestDTO;
import com.zerobeta.ordermanagementAPI.Fixture.ClientFixture;
import com.zerobeta.ordermanagementAPI.Fixture.DTOFixture;
import com.zerobeta.ordermanagementAPI.Model.Client;
import com.zerobeta.ordermanagementAPI.Model.ClientPrincipal;
import com.zerobeta.ordermanagementAPI.Repository.ClientRepo;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
    @Mock
    private ClientRepo clientRepo;

    @InjectMocks
    private ClientService clientService;

    private Client client;

    @BeforeEach
    public void setUp() {
        client = ClientFixture.createClient("1");
    }

    @Test
    void testGetClient() {
        when(clientRepo.findById(client.getId())).thenReturn(Optional.of(client));
        Client foundClient = clientService.getClient(client.getId());

        assertEquals(client, foundClient);
    }

    @Test
    void testGetClientNotFound() {
        UUID clientId = UUID.randomUUID();

        when(clientRepo.findById(clientId)).thenReturn(Optional.empty());

        Client result = clientService.getClient(clientId);

        assertNull(result);
        verify(clientRepo, times(1)).findById(clientId);
    }

    @Test
    void testGetAllClients() {
        List<Client> clients = Arrays.asList(new Client(), new Client());

        when(clientRepo.findAll()).thenReturn(clients);

        List<Client> result = clientService.getAllClients();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(clientRepo, times(1)).findAll();
    }

    @Test
    void testLoadUserByUsername_Found() {
        String email = "test@example.com";
        Client client = new Client();
        client.setEmail(email);

        when(clientRepo.findByEmail(email)).thenReturn(Optional.of(client));

        UserDetails userDetails = clientService.loadUserByUsername(email);

        assertNotNull(userDetails);
        assertEquals(email, ((ClientPrincipal) userDetails).getUsername());
        verify(clientRepo, times(1)).findByEmail(email);
    }

    @Test
    void testLoadUserByUsername_NotFound() {
        String email = "notfound@example.com";

        when(clientRepo.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> clientService.loadUserByUsername(email));
        verify(clientRepo, times(1)).findByEmail(email);
    }

    @Test
    void testcreateFromRegisterRequestDTO() {
        RegisterRequestDTO dto = DTOFixture.createRegisterRequestDTO();

        Client client = clientService.createFromRegisterRequestDTO(dto);

        assertThat(client.getFirst_name()).isEqualTo(dto.getFirst_name());
        assertThat(client.getLast_name()).isEqualTo(dto.getLast_name());
        assertThat(client.getEmail()).isEqualTo(dto.getEmail());
    }
}
