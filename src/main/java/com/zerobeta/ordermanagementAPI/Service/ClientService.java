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

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client client = clientRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new ClientPrincipal(client);
    }
}
