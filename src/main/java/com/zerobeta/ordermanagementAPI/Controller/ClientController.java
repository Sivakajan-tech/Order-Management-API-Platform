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
import com.zerobeta.ordermanagementAPI.DTO.LoginRequestDTO;
import com.zerobeta.ordermanagementAPI.DTO.RegisterRequestDTO;
import com.zerobeta.ordermanagementAPI.Model.Client;
import com.zerobeta.ordermanagementAPI.Service.ClientService;
import com.zerobeta.ordermanagementAPI.Service.JWTService;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;

/**
 * The ClientController class is a REST controller that handles HTTP requests
 * related to client operations as well as the Authentication endpoint.
 */
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
        /*
         * Endpoint: GET /api/clients
         * Function: Retrieve all clients.
         */
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> findById(@PathVariable UUID id) {
        /*
         * Endpoint: GET /api/clients/{id}
         * Function: Find a client by their unique ID.
         * 
         * @param id: The unique ID of the client.
         */
        Client fetchedClient = clientService.getClient(id);

        if (fetchedClient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(fetchedClient, HttpStatus.OK);
    }

    @Transactional // Ensures that the registerClient method is executed within a transaction.
    @PostMapping("/register")
    public ResponseEntity<Client> registerClient(@RequestBody @Valid RegisterRequestDTO registerRequest) {
        /*
         * Endpoint: POST /api/clients/register
         * Function: Register a new client.
         * 
         * @param registerRequest: The registration details of the client.
         */
        Client fetchedClient = Client.fromRegisterRequestDTO(registerRequest);
        return new ResponseEntity<>(clientService.addClient(fetchedClient), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequestDTO loginRequest) {
        /*
         * Endpoint: POST /api/clients/login
         * Function: Authenticate a client and generate a JWT token.
         * 
         * @param loginRequest: The login details of the client.
         */
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        String jwtToken = jwtService.generateToken(loginRequest.getEmail());
        return ResponseEntity.ok(jwtToken);
    }
}
