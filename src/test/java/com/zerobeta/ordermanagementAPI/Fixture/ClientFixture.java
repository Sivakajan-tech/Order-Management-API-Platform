package com.zerobeta.ordermanagementAPI.Fixture;

import org.junit.jupiter.api.Test;
import com.zerobeta.ordermanagementAPI.Model.Client;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientFixture {
    public static Client createClient(String prefix) {
        return new Client(
                null,
                prefix + "first_name",
                prefix + "_last_name",
                prefix + "_email",
                prefix + "_password");
    }

    @Test
    void testClientToString() {
        String prefix = "test_";
        Client client = createClient(prefix);

        String expectedString = "Client [id=null, first_name=test_first_name, last_name=test__last_name, email=test__email]";
        assertThat(client.toString()).isEqualTo(expectedString);
    }
}
