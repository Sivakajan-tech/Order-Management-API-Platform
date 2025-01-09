package com.zerobeta.ordermanagementAPI.Service;

import com.zerobeta.ordermanagementAPI.DTO.RegisterRequestDTO;
import com.zerobeta.ordermanagementAPI.Model.Client;
import com.zerobeta.ordermanagementAPI.Model.ClientPrincipal;
import com.zerobeta.ordermanagementAPI.Repository.ClientRepo;
import com.zerobeta.ordermanagementAPI.Utils.Encoders;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClientService implements UserDetailsService {
    @Autowired
    private ClientRepo clientRepo;

    public Client getClient(UUID id) {
        return clientRepo.findById(id).orElse(null);
    }

    public Client addClient(RegisterRequestDTO requestDTO) {
        Client newClient = createFromRegisterRequestDTO(requestDTO);
        Client addeduser = clientRepo.save(newClient);
        return addeduser;
    }

    public List<Client> getAllClients() {
        return clientRepo.findAll();
    }

    /*
     * Loads the user details by the given email.
     * This method is used to retrieve user information from the database
     * based on the provided email address. It is a part of the UserDetailsService
     * interface which is used by Spring Security to perform authentication and
     * authorization.
     */
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client client = clientRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new ClientPrincipal(client);
    }

    // Factory method for creating an instance from RegisterRequestDTO
    public Client createFromRegisterRequestDTO(RegisterRequestDTO dto) {
        Client client = new Client();
        client.setFirst_name(dto.getFirst_name());
        client.setLast_name(dto.getLast_name());
        client.setEmail(dto.getEmail());
        client.setPassword(Encoders.passwordEncoder(dto.getPassword()));

        return client;
    }
}
