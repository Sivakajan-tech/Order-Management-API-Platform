package com.zerobeta.ordermanagementAPI.Service;

import com.zerobeta.ordermanagementAPI.Model.Client;
import com.zerobeta.ordermanagementAPI.Model.ClientPrincipal;
import com.zerobeta.ordermanagementAPI.Repository.ClientRepo;
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

    public Client addClient(Client client) {
        Client addeduser = clientRepo.save(client);
        return addeduser;
    }

    public List<Client> getAllClients() {
        return clientRepo.findAll();
    }

    /**
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
}
