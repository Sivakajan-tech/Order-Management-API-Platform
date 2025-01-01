package com.zerobeta.ordermanagementAPI.Repository;

import com.zerobeta.ordermanagementAPI.Fixture.ClientFixture;
import com.zerobeta.ordermanagementAPI.Model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ClientRepoTest {
    @Autowired
    ClientRepo clientRepo;

    private Client client1;
    private Client client2;

    @BeforeEach
    void setUp() {
        client1 = ClientFixture.createClient("1");
        clientRepo.save(client1);

        client2 = ClientFixture.createClient("2");
        clientRepo.save(client2);
    }

    @Test
    void testFindClientByEmail() {
        Optional<Client> foundClient = clientRepo.findByEmail(client1.getEmail());

        assertThat(foundClient).isPresent();
        assertThat(foundClient.get().getEmail()).isEqualTo(client1.getEmail());
    }

    @Test
    void testFindClientByEmailWhenRandomEmail() {
        Optional<Client> foundClient = clientRepo.findByEmail("RandomEmail@email.com");

        assertThat(foundClient).isEmpty();
    }

    @Test
    void testFindClientById() {
        Optional<Client> foundClient = clientRepo.findById(client1.getId());

        assertThat(foundClient).isPresent();
        assertThat(foundClient.get().getEmail()).isEqualTo(client1.getEmail());
    }

    @Test
    void testFindClientByRandomID() {
        UUID randomId = UUID.randomUUID();
        Optional<Client> foundClient = clientRepo.findById(randomId);

        assertThat(foundClient).isEmpty();
    }

    @Test
    void testSaveClient() {
        Client new_client = new Client();
        new_client.setFirst_name("first_name");
        new_client.setLast_name("last_name");
        new_client.setEmail("email");
        new_client.setPassword("password");

        Client savedClient = clientRepo.save(new_client);

        assertThat(savedClient).isNotNull();
        assertThat(savedClient.getId()).isEqualTo(new_client.getId());
        assertThat(savedClient.getEmail()).isEqualTo("email");
    }

    @Test
    void testFindAllClients() {
        List<Client> clients = clientRepo.findAll();

        assertThat(clients).isNotEmpty();
        assertThat(clients).hasSize(2);
    }
}
