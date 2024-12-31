package com.zerobeta.ordermanagementAPI.Config;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.zerobeta.ordermanagementAPI.Service.JWTService;
import com.zerobeta.ordermanagementAPI.Service.ClientService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * A custom filter to handle JWT-based authentication. It intercepts each HTTP
 * request to check for a valid JWT token in the Authorization header.
 * 
 * The filter checks for the "Authorization" header with a "Bearer " prefix,
 * extracts the token, validates it, and sets the authentication in the
 * SecurityContext if the token is valid.
 */
@Component
public class JWTFilter extends OncePerRequestFilter {
    private final String authHeaderPrefix = "Bearer ";

    @Autowired
    private JWTService jwtService;

    @Autowired
    private ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String authEmail = null;

        if (authHeader != null && authHeader.startsWith(authHeaderPrefix)) {
            token = authHeader.substring(authHeaderPrefix.length()); // Extract the token from the Authorization header
            authEmail = jwtService.extractUserName(token); // Extract the email from the token
        }
        if (authEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = context
                    .getBean(ClientService.class)
                    .loadUserByUsername(authEmail);

            // If a valid token is found, it extracts the user details and sets the
            // authentication in the SecurityContext. This filter ensures that only
            // authenticated users can access protected resources.
            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());
                authToken
                        .setDetails(new WebAuthenticationDetailsSource()
                                .buildDetails(request));
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authToken);
            }
        }
        
        filterChain.doFilter(request, response);
    }
}
