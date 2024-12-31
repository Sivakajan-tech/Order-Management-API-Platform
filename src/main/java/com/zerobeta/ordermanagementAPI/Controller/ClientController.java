package com.zerobeta.ordermanagementAPI.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zerobeta.ordermanagementAPI.Model.Client;
import com.zerobeta.ordermanagementAPI.Service.ClientService;
import com.zerobeta.ordermanagementAPI.Service.JWTService;

@RestController
// @CrossOrigin // This annotation is used to handle the request from a
// different origin
@RequestMapping("/api/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @GetMapping("")
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> findById(@PathVariable UUID id) {
        Client fetchedClient = clientService.getClient(id);

        if (fetchedClient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(fetchedClient, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Client> registerClient(@RequestBody Client client) {
        System.out.println("Hi" + client);
        return new ResponseEntity<>(clientService.addClient(client), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Client client) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(client.getEmail(), client.getPassword()));

        String jwtToken = jwtService.generateToken(client.getEmail());
        return ResponseEntity.ok(jwtToken);
    }
}
