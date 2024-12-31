package com.zerobeta.ordermanagementAPI.Fixture;

import java.time.LocalDateTime;
import com.zerobeta.ordermanagementAPI.Common.Enums.OrderStatus;
import com.zerobeta.ordermanagementAPI.Model.Client;
import com.zerobeta.ordermanagementAPI.Model.Order;

public class OrderFixture {

    public static Order createOrder(String prefix) {
        Client client = ClientFixture.createClient(prefix);
        return new Order(
                null,
                prefix + "_Name",
                LocalDateTime.now(),
                prefix + "_shippingAddress",
                1, client,
                OrderStatus.NEW);
    }
}
