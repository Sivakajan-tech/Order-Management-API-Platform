package com.zerobeta.ordermanagementAPI.Model;

import java.util.Collection;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.AllArgsConstructor;

/**
 * The ClientPrincipal class implements the UserDetails interface to provide
 * user-specific information for authentication and authorization purposes.
 * This class is used by Spring Security to retrieve user details such as
 * username, password, and authorities, which are essential for the security
 * framework to perform authentication and role-based access control.
 * 
 * The ClientPrincipal class wraps around a Client object and delegates
 * the retrieval of user details to the Client instance.
 */

@AllArgsConstructor
public class ClientPrincipal implements UserDetails {
    @Autowired
    private Client client;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return this.client.getPassword();
    }

    @Override
    public String getUsername() {
        return this.client.getEmail();
    }
}
