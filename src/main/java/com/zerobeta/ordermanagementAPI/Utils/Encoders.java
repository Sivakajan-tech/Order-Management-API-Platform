package com.zerobeta.ordermanagementAPI.Utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * This class provides a different type of encoders for various applications.
 */
@Component
public class Encoders {
    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public static String passwordEncoder(String password) {
        return encoder.encode(password);
    }
}
