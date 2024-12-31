package com.zerobeta.ordermanagementAPI.Repository;

import com.zerobeta.ordermanagementAPI.Common.Enums.OrderStatus;
import com.zerobeta.ordermanagementAPI.Model.Client;
import com.zerobeta.ordermanagementAPI.Model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderRepoTest {
    UUID clientId = UUID.randomUUID();
    Client client = new Client(null, "first_name", "last_name", "email", "password");
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    ClientRepo clientRepo;

    private Order testOrder;

    @BeforeEach
    void setUp() {
        Client savedClient = clientRepo.save(client);

        // Create and save a test order
        testOrder = new Order();
        testOrder.setOrderName("test_order");
        testOrder.setClient(client);
        testOrder.setShipping_address("test_address");
        testOrder.setQuantity(2);
        testOrder = orderRepo.save(testOrder);
    }

    @Test
    void testFindByOrderId() {
        Optional<Order> foundOrder = orderRepo.findByOrderId(testOrder.getOrderId());

        assertThat(foundOrder).isPresent();
        assertThat(foundOrder.get().getOrderName()).isEqualTo(testOrder.getOrderName());
    }

    @Test
    void testFindByStatus() {
        List<Order> orders = orderRepo.findByStatus(OrderStatus.NEW);

        assertThat(orders).isNotEmpty();
        assertThat(orders.get(0).getStatus()).isEqualTo(OrderStatus.NEW);
    }

    @Test
    void testFindByClientId() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Order> ordersPage = orderRepo.findByClientId(testOrder.getClient().getId(), pageRequest);

        assertThat(ordersPage).isNotEmpty();
        assertThat(ordersPage.getContent().get(0).getClient().getId()).isEqualTo(testOrder.getClient().getId());
    }

    @Test
    void testSaveOrder() {
        Order newOrder = new Order();
        newOrder.setStatus(OrderStatus.DISPATCHED);

        Order savedOrder = orderRepo.save(newOrder);

        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getOrderId()).isEqualTo(newOrder.getOrderId());
        assertThat(savedOrder.getStatus()).isEqualTo(OrderStatus.DISPATCHED);
    }
}
