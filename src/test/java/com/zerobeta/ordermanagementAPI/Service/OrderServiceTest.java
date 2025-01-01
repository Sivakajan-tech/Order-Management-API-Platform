package com.zerobeta.ordermanagementAPI.Service;

import com.zerobeta.ordermanagementAPI.Common.Enums.OrderStatus;
import com.zerobeta.ordermanagementAPI.DTO.OrderRequestDTO;
import com.zerobeta.ordermanagementAPI.DTO.OrderResponseDTO;
import com.zerobeta.ordermanagementAPI.Fixture.ClientFixture;
import com.zerobeta.ordermanagementAPI.Fixture.OrderFixture;
import com.zerobeta.ordermanagementAPI.Model.Client;
import com.zerobeta.ordermanagementAPI.Model.Order;
import com.zerobeta.ordermanagementAPI.Repository.OrderRepo;
import com.zerobeta.ordermanagementAPI.Service.Utils.SecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepo orderRepo;

    @Mock
    private SecurityService securityService;

    @InjectMocks
    private OrderService orderService;

    private Client client;
    private OrderRequestDTO orderRequestDTO;
    private Order order1;
    private Order order2;

    @BeforeEach
    void setUp() {
        client = ClientFixture.createClient("1");
        order1 = OrderFixture.createOrder("1");
        order2 = OrderFixture.createOrder("2");

        orderRequestDTO = new OrderRequestDTO("item1", "123 Main St", 2);
    }

    @Test
    void testGetAllOrders() {
        List<Order> orders = Arrays.asList(order1, order2);
        when(orderRepo.findAll()).thenReturn(orders);

        List<OrderResponseDTO> result = orderService.getAllOrders();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(orderRepo, times(1)).findAll();
    }

    @Test
    void testGetOrder_Found() {
        UUID orderId = UUID.randomUUID();
        when(orderRepo.findByOrderId(orderId)).thenReturn(Optional.of(order1));

        OrderResponseDTO result = orderService.getOrder(orderId);

        assertNotNull(result);
        verify(orderRepo, times(1)).findByOrderId(orderId);
    }

    @Test
    void testGetOrder_NotFound() {
        UUID orderId = UUID.randomUUID();
        when(orderRepo.findByOrderId(orderId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> orderService.getOrder(orderId));
        verify(orderRepo, times(1)).findByOrderId(orderId);
    }

    @Test
    void testUpdateNewOrdersToDispatched() {
        List<Order> orders = Arrays.asList(order1, order2);
        when(orderRepo.findByStatus(OrderStatus.NEW)).thenReturn(orders);

        orderService.updateNewOrdersToDispatched();

        for (Order order : orders) {
            assertEquals(OrderStatus.DISPATCHED, order.getStatus());
        }
        verify(orderRepo, times(1)).findByStatus(OrderStatus.NEW);
        verify(orderRepo, times(orders.size())).save(any(Order.class));
    }

    @Test
    void testCreateOrder() {
        UUID orderId = UUID.randomUUID();
        when(securityService.getAuthenticatedClient()).thenReturn(client);
        when(orderRepo.save(any(Order.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            order.setOrderId(orderId);
            return order;
        });

        OrderResponseDTO response = orderService.createOrder(orderRequestDTO);

        assertNotNull(response);
        assertEquals(orderId, response.getId());
        assertEquals("item1", response.getItemName());
        assertEquals(2, response.getQuantity());
        assertEquals(OrderStatus.NEW, response.getStatus());
    }

    @Test
    void testCancelOrder_Success() {
        when(orderRepo.save(order1)).thenReturn(order1);
        Order savedOrder = orderRepo.save(order1);
        UUID orderId = savedOrder.getOrderId();

        when(orderRepo.findByOrderId(orderId)).thenReturn(Optional.of(savedOrder));
        OrderResponseDTO result = orderService.cancelOrder(orderId);

        assertNotNull(result);
        assertEquals(OrderStatus.CANCELLED, order1.getStatus());
        verify(orderRepo, times(1)).findByOrderId(orderId);
    }

    @Test
    void testCancelOrder_OrderNotInNewStatus() {
        order1.setStatus(OrderStatus.DISPATCHED);
        when(orderRepo.save(order1)).thenReturn(order1);
        Order savedOrder = orderRepo.save(order1);
        UUID orderId = savedOrder.getOrderId();

        when(orderRepo.findByOrderId(orderId)).thenReturn(Optional.of(order1));

        assertThrows(IllegalStateException.class, () -> orderService.cancelOrder(orderId));
        verify(orderRepo, times(1)).findByOrderId(orderId);
    }

    @Test
    void testCancelOrder_NotFound() {
        UUID orderId = UUID.randomUUID();
        when(orderRepo.findByOrderId(orderId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> orderService.cancelOrder(orderId));
        verify(orderRepo, times(1)).findByOrderId(orderId);
    }

    @Test
    void testGetOrderHistory_Found() {
        when(securityService.getAuthenticatedClient()).thenReturn(client);

        List<Order> orders = Arrays.asList(order1, order2);
        Page<Order> orderPage = new PageImpl<>(orders);
        when(orderRepo.findByClientId(eq(client.getId()), any(PageRequest.class))).thenReturn(orderPage);

        List<OrderResponseDTO> result = orderService.getOrderHistory(0, 2);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(orderRepo, times(1)).findByClientId(eq(client.getId()), any(PageRequest.class));
    }

    @Test
    void testGetOrderHistory_NotFound() {
        when(securityService.getAuthenticatedClient()).thenReturn(client);

        Page<Order> emptyPage = Page.empty();
        when(orderRepo.findByClientId(eq(client.getId()), any(PageRequest.class))).thenReturn(emptyPage);

        assertThrows(IllegalArgumentException.class, () -> orderService.getOrderHistory(0, 2));
        verify(orderRepo, times(1)).findByClientId(eq(client.getId()), any(PageRequest.class));
    }
}
