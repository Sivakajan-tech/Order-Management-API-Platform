package com.zerobeta.ordermanagementAPI.Utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encoders {

    public static String passwordEncoder(String password) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
