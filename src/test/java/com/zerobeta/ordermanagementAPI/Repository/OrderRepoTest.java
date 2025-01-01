package com.zerobeta.ordermanagementAPI.Repository;

import com.zerobeta.ordermanagementAPI.Common.Enums.OrderStatus;
import com.zerobeta.ordermanagementAPI.Fixture.ClientFixture;
import com.zerobeta.ordermanagementAPI.Fixture.OrderFixture;
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

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    ClientRepo clientRepo;

    private Client client;
    private Order testOrder1;
    private Order testOrder2;

    @BeforeEach
    void setUp() {
        client = ClientFixture.createClient("1");
        clientRepo.save(client);

        // Create and save a test order
        testOrder1= OrderFixture.createOrderByClient("1", client);
        testOrder1 = orderRepo.save(testOrder1);

        // Create and save a test order
        testOrder2 = OrderFixture.createOrderByClient("1", client);
        testOrder2.setStatus(OrderStatus.DISPATCHED);
        testOrder2 = orderRepo.save(testOrder2);
    }

    @Test
    void testFindByOrderId() {
        Optional<Order> foundOrder = orderRepo.findByOrderId(testOrder1.getOrderId());

        assertThat(foundOrder).isPresent();
        assertThat(foundOrder.get().getOrderName()).isEqualTo(testOrder1.getOrderName());
    }

    @Test
    void testFindByStatus() {
        List<Order> orders = orderRepo.findByStatus(OrderStatus.NEW);

        assertThat(orders).isNotEmpty();
        assertThat(orders).hasSize(1);
        assertThat(orders.get(0).getStatus()).isEqualTo(OrderStatus.NEW);
    }

    @Test
    void testFindByStatusZeroOrders() {
        List<Order> orders = orderRepo.findByStatus(OrderStatus.CANCELLED);

        assertThat(orders).isEmpty();
    }

    @Test
    void testFindByClientIdWith0thPage() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Order> ordersPage = orderRepo.findByClientId(testOrder1.getClient().getId(), pageRequest);

        assertThat(ordersPage).isNotEmpty();
        assertThat(ordersPage.getContent()).hasSize(2);
        assertThat(ordersPage.getContent().get(0).getClient().getId()).isEqualTo(testOrder1.getClient().getId());
    }

    @Test
    void testFindByClientIdWithNon0thPage() {
        PageRequest pageRequest = PageRequest.of(1, 1);
        Page<Order> ordersPage = orderRepo.findByClientId(testOrder1.getClient().getId(), pageRequest);

        assertThat(ordersPage).isNotEmpty();
        assertThat(ordersPage.getContent()).hasSize(1);
        assertThat(ordersPage.getContent().get(0).getClient().getId()).isEqualTo(testOrder1.getClient().getId());
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
