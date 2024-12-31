package com.zerobeta.ordermanagementAPI.Fixture;

import java.util.UUID;

import com.zerobeta.ordermanagementAPI.Model.Client;

public class ClientFixture {
    public static Client createClient(String prefix) {
        return new Client(
                UUID.randomUUID(),
                prefix + "first_name",
                prefix + "_last_name",
                prefix + "_email",
                prefix + "_password");
    }
}
