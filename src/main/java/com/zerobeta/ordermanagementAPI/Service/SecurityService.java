package com.zerobeta.ordermanagementAPI.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.zerobeta.ordermanagementAPI.Model.Client;
import com.zerobeta.ordermanagementAPI.Repository.ClientRepo;

@Service
public class SecurityService {
    @Autowired
    private ClientRepo clientRepo;

    public Client getAuthenticatedClient() {
        // Retrieve the authenticated user's details from the SecurityContext
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String email = ((UserDetails) principal).getUsername();
            // Find and return the client by email
            return clientRepo.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
