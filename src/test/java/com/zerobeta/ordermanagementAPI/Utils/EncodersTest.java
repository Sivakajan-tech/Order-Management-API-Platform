package com.zerobeta.ordermanagementAPI.Utils;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

class EncodersTest {

    @Test
    void testPasswordEncoder() {
        // Arrange
        String rawPassword = "testPassword123";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

        // Act
        String encodedPassword = Encoders.passwordEncoder(rawPassword);

        // Assert
        assertThat(encodedPassword).isNotNull();
        assertThat(encodedPassword).isNotEmpty();
        assertThat(encoder.matches(rawPassword, encodedPassword)).isTrue();
    }
}
