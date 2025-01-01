package com.zerobeta.ordermanagementAPI.Fixture;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.zerobeta.ordermanagementAPI.Common.Enums.OrderStatus;
import com.zerobeta.ordermanagementAPI.DTO.OrderRequestDTO;
import com.zerobeta.ordermanagementAPI.Model.Client;
import com.zerobeta.ordermanagementAPI.Model.Order;

import static org.assertj.core.api.Assertions.assertThat;

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

    public static Order createOrderByClient(String prefix, Client client) {
        return new Order(
                null,
                prefix + "_Name",
                LocalDateTime.now(),
                prefix + "_shippingAddress",
                1, client,
                OrderStatus.NEW);
    }

    @Test
    void testFromOrderRequestDTO() {
        OrderRequestDTO orderRequestDTO = DTOFixture.createOrderRequestDTO();

        Client client = ClientFixture.createClient("1");

        Order order = Order.fromOrderRequestDTO(orderRequestDTO, client);

        assertThat(order).isNotNull();
        assertThat(order.getOrderName()).isEqualTo(orderRequestDTO.getName());
        assertThat(order.getQuantity()).isEqualTo(orderRequestDTO.getQuantity());
        assertThat(order.getShipping_address()).isEqualTo(orderRequestDTO.getShipping_address());
        assertThat(order.getStatus()).isEqualTo(OrderStatus.NEW);
        assertThat(order.getClient()).isEqualTo(client);
    }
}
