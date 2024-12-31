package com.zerobeta.ordermanagementAPI.DTO;

import com.zerobeta.ordermanagementAPI.Model.Client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequestDTO {
    @NotBlank(message = "First name cannot be empty")
    private String first_name;

    @NotBlank(message = "Last name cannot be empty")
    private String last_name;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email")
    private String email;

    // We can add more constraints to the password field but I keep this simple
    @NotBlank(message = "Password cannot be empty")
    private String password;
}
