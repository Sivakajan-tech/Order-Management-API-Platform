package com.zerobeta.ordermanagementAPI.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfig class configures the security settings for the application.
 * It enables web security, configures JWT authentication, and sets up password
 * encoding.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JWTFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        /*
         * This configuration class does the following:
         * 
         * Disables CSRF protection as it is not needed for stateless REST APIs.
         * Allows unauthenticated access to the login and register endpoints.
         * Requires authentication for all other endpoints.
         * Configures the session management to be stateless.
         * Adds a JWT filter before the UsernamePasswordAuthenticationFilter to handle
         * JWT token validation.
         */
        httpSecurity.csrf(customiser -> customiser.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/clients/login", "/api/clients/register")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder(12);
    }
}
