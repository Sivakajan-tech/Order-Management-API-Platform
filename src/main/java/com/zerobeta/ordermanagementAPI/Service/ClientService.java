package com.zerobeta.ordermanagementAPI.Service;

import com.zerobeta.ordermanagementAPI.Model.Client;
import com.zerobeta.ordermanagementAPI.Model.ClientPrincipal;
import com.zerobeta.ordermanagementAPI.Repository.ClientRepo;

import java.util.*;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientService implements UserDetailsService {
    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    private Logger logger;

    public ClientService() {
        this.logger = Logger.getLogger(ClientService.class.getName());
    }

    public Client getClient(UUID id) {
        return clientRepo.findById(id).orElse(null);
    }

    public Client addClient(Client client) {
        client.setPassword(encoder.encode(client.getPassword()));
        Client addeduser = clientRepo.save(client);
        logger.info("User added: " + client.getId());
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
