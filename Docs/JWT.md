# JWT Authentication in a Spring Boot Application

JSON Web Tokens (JWT) have become a popular method for securing modern web applications. It provides a flexible and stateless way to verify users' identities and secure API endpoints; it is also called Token-Based Authentication. JWTs allow you to transmit information securely between parties as a compact, self-contained, and digitally signed JSON object.  

![ER Diagram](/statics/Images/jwt.png)

## Overview when accessing the resources

![JWT overall flow](/statics/Images/JWT-overall.webp)

This process is secured by Spring Security, Let’s examine its flow as follows.

 The process starts when the user sends a request to the Service. The request is first intercepted by `JWTFilter`, which is a custom filter integrated into the SecurityFilterChain.
 
 - If the JWT is missing, a response with HTTP Status 403 is sent to the user.
 - When an existing JWT is received, JwtService is called to extract the userEmail from the JWT.
   - If the userEmail cannot be extracted, a response with HTTP Status 403 is sent to the user.
   - If the userEmail can be extracted, it will be used to query the user’s authentication and authorization information via UserDetailsService.
     - If the user’s authentication and authorization information does not exist in the database, a response with HTTP Status 403 is sent to the user.
     - If the JWT is expired, a response with HTTP Status 403 is sent to the user.
 
 Upon successful authentication:
 - The user’s details are encapsulated in a UsernamePasswordAuthenticationToken object and stored in the SecurityContextHolder.
 - The Spring Security Authorization process is automatically invoked.
 - The request is dispatched to the controller, and a successful JSON response is returned to the user.
 

## Key Components of Spring Security Architecture

Refer Following Chart to Understand the Architecture,
![JWT Architecture](/statics/Images/jwt-archi.webp)


### SecurityContextHolder
 * Manages the security context, which holds the details of the current authentication and authorization information.
 * The `SecurityContextHolder` provides access to the `SecurityContext`, and the `SecurityContext` contains the principal (authenticated user) and their granted authorities.
 * We can set the authentication to `SecurityContext` at login and after JwtToken authenticated.

### AuthenticationManager (Interface)
 * Responsible for authenticating a user based on their credentials.
 * Spring Security uses `AuthenticationManager` to authenticate the user during the login process.
 * The default implementation is `ProviderManager`, which delegates to a list of `AuthenticationProvider` instances.
 
### AuthenticationProvider
 * Implementation of `AuthenticationManager` and can override the `authenticate()`.
 * Custom authentication logic can be implemented by creating a class that implements the `AuthenticationProvider` interface.
 * Common implementations include `DaoAuthenticationProvider` (used in this example) for database-backed authentication and `JwtAuthenticationProvider` for JWT-based authentication.
 * Need to provide `UserDetailsService` (loads the user from DB and sets the UserDetails) and PasswordEncoder (since the password will be saved in DB after encoding) for `DaoAuthenticationProvider`.
 
### UserDetailsService
 * Interface used to load user-specific data during the authentication process.
 * Implementations of `UserDetailsService` retrieve user details (such as username, password, and authorities) from a data source (e.g., a database) and create a UserDetails object.
 * Commonly used with `DaoAuthenticationProvider`.
 
### UserDetails
 * Represents the principal (user) details, including username, password, and authorities.
 * Spring Security uses UserDetails to store information about the authenticated user.
 
### GrantedAuthority
 * Represents an authority granted to a user.
 * Authorities are typically roles or permissions that define what actions a user can perform.
 * Implementations include SimpleGrantedAuthority.
 
### Authentication
 * When login authentication carries credentials in `UsernamePasswordAuthenticationToken`.
 * Represents the token for an authenticated user (principal) after authentication (`UsernamePasswordAuthenticationToken` created with UserDetails).
 * Contains the principal (authenticated user), granted authorities, and details about the authentication process.
 
### FilterChain
 * Consists of a series of filters that process incoming requests.
 * Spring Security uses a chain of filters to perform various security-related tasks, such as authentication, authorization, and session management.
 
### UsernamePasswordAuthenticationToken
 * The `UsernamePasswordAuthenticationToken` is a class in Spring Security that represents an authentication token for username and password-based authentication. It implements the Authentication interface, which is the core interface representing an authenticated principal once the authentication process is completed.
 
### UserDetails
 * Implementations of the UserDetails interface are typically used to represent user details loaded during the authentication process. Consist of methods like getUsername(), getPassword(), getAuthorities(), isEnabled(), etc.
 
## Source

This document is based on the articles [Spring Security Architecture Explained with JWT Authentication Example (Spring Boot)](https://medium.com/@dilankacm/spring-security-architecture-explained-with-jwt-authentication-example-spring-boot-5cc583a9aeac) by Dilanka Chandima and [JWT Authentication and Authorization with Spring Boot 3 and Spring Security 6](https://medium.com/@truongbui95/jwt-authentication-and-authorization-with-spring-boot-3-and-spring-security-6-2f90f9337421) by Truong Bui.

