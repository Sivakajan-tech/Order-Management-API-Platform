package com.zerobeta.ordermanagementAPI.Model;

import java.util.UUID;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.zerobeta.ordermanagementAPI.DTO.RegisterRequestDTO;
import com.zerobeta.ordermanagementAPI.Utils.Encoders;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Entity
@Table(name = "`clients`")
@Data // Lombok annotation to create all getters, setters, equals, hash, and toString
      // methods
@NoArgsConstructor
@AllArgsConstructor
@Scope("prototype")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "client_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String first_name;

    @Column(name = "last_name", nullable = false)
    private String last_name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Override
    public String toString() {
        return "Client [id=" + id + ", first_name=" + first_name + ", last_name=" + last_name + ", email=" + email
                + "]";
    }

    // Factory method for creating an instance from RegisterRequestDTO
    public static Client fromRegisterRequestDTO(RegisterRequestDTO dto) {
        Client client = new Client();
        client.setFirst_name(dto.getFirst_name());
        client.setLast_name(dto.getLast_name());
        client.setEmail(dto.getEmail());
        client.setPassword(Encoders.passwordEncoder(dto.getPassword()));

        return client;
    }

}
